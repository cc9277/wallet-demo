package com.lpf.user.service;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lpf.user.api.dto.UserDto;

import com.lpf.user.api.myenum.CodeEnum;
import com.lpf.user.api.pojos.Account;
import com.lpf.user.api.pojos.User;
import com.lpf.user.api.vo.ResponseResult;
import com.lpf.user.mapper.AccountMapper;
import com.lpf.user.mapper.UserCenterMapper;
import com.lpf.user.api.service.UserCenterService;
import com.lpf.user.utils.SnowFlake;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;


import java.time.Duration;
import java.util.Date;
import java.util.HashMap;

@Service
public class UserCenterServiceImpl implements UserCenterService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private UserCenterMapper userCenterMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Value("${jwt.secret}")
    //加密盐
    private  String secret;
    @Override
    public ResponseResult register(UserDto userDto) {
        if (userDto==null){
            return ResponseResult.errorResult(CodeEnum.PARAM_INVALID);
        }
        String phone=userDto.getPhone();
        String redisKey="USER_CODE_"+phone;
        String redisValue = redisTemplate.opsForValue().get(redisKey);
        //比较传过来的验证码,过期或者不相等就注册失败
        if (!StringUtils.equals(userDto.getCode(),redisValue)){
            return ResponseResult.errorResult(CodeEnum.CODE_ERROR);
        }
        //验证通过就删除缓存
        redisTemplate.delete(redisKey);
        //判断该手机号是否已经被使用
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getPhone,phone);
        User user1 = this.userCenterMapper.selectOne(lambdaQueryWrapper);
        if (user1!=null){
            //手机号已经被注册，直接返回
            return ResponseResult.errorResult(CodeEnum.PHONE_EXIT);
        }
        Account account = new Account();
        account.setAccountName("白马账户");
        account.setBalance(0.0);
        account.setCreateTime(new Date());
        account.setUpdateTime(new Date());
        SnowFlake snowFlake = new SnowFlake(0, 0);
        //雪花算法生成id
        account.setAccountId(snowFlake.nextId());
        //注册用户同时注册一个账户,每个用户只有一个账户
        accountMapper.insert(account);
        String json = JSON.toJSONString(userDto);
        User user = JSON.parseObject(json, User.class);
        user.setCreateTime(new Date());
        user.setAccountId(account.getAccountId());
        //写入数据库
        this.userCenterMapper.insert(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult login(String phone, String code) {
        if (StringUtils.isEmpty(phone)||StringUtils.isEmpty(code)){
            return ResponseResult.errorResult(CodeEnum.PHONE_ERROR);
        }
        String redisKey="USER_CODE_"+phone;
        String redisValue = redisTemplate.opsForValue().get(redisKey);
        //比较传过来的验证码,过期或者不相等就登录失败
        if (!StringUtils.equals(code,redisValue)){
            return ResponseResult.errorResult(CodeEnum.CODE_ERROR);
        }
        //验证通过就删除缓存
        redisTemplate.delete(redisKey);
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getPhone,phone);
        User user = this.userCenterMapper.selectOne(lambdaQueryWrapper);
        //用户不存在，设置参数存进去
        if (user==null){
            Account account = new Account();
            account.setAccountName("白马账户");
            account.setBalance(0.0);
            account.setCreateTime(new Date());
            account.setUpdateTime(new Date());
            SnowFlake snowFlake = new SnowFlake(0, 0);
            account.setAccountId(snowFlake.nextId());
            //注册用户同时注册一个账户,每个用户只有一个账户
            accountMapper.insert(account);
            user=new User();
            //设置初始密码
            user.setPassword("123456");
            user.setAccountId(account.getAccountId());
            user.setCreateTime(new Date());
            user.setPhone(phone);

            //采取默认名字
            user.setUsername("白马钱包用户");

            //存入数据库
            userCenterMapper.insert(user);
        }
        Long userId = user.getId();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userId",userId+"");
        hashMap.put("accountId",user.getAccountId()+"");
        long currentTimeMillis = System.currentTimeMillis();
        //生成token
        String  token= Jwts.builder()
                .setClaims(hashMap)//payload存储的数据
                .signWith(SignatureAlgorithm.HS256, secret)//加密方式和盐
                .setExpiration(new Date(currentTimeMillis + 3600 * 1000))//过期时间
                .compact();
        return ResponseResult.okResult(token);

    }

    @Override
    public ResponseResult getCode(String phone){
        if (StringUtils.isEmpty(phone)){
            return ResponseResult.errorResult(CodeEnum.PHONE_ERROR);
        }
        String code="123456";
        //存入redis,设置5分钟过期时间
        String redisKey="USER_CODE_"+phone;
        redisTemplate.opsForValue().set(redisKey,code, Duration.ofMinutes(5));
        return ResponseResult.okResult();
    }
}
