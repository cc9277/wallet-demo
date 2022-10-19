package com.lpf.wallet.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lpf.wallet.dto.TradeDto;
import com.lpf.wallet.mapper.AccountMapper;
import com.lpf.wallet.mapper.TradeMapper;
import com.lpf.wallet.myenum.CodeEnum;
import com.lpf.wallet.pojo.Account;
import com.lpf.wallet.pojo.Trade;
import com.lpf.wallet.pojo.User;
import com.lpf.wallet.vo.ResponseResult;
import io.jsonwebtoken.Jwts;


import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
public class WalletFunctionServiceImpl implements WalletFunctionService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private TradeMapper tradeMapper;
    @Value("${jwt.secret}")
    private String secret;

    /**
     *资金管理
     * @param tradeDto
     * @param token
     * @return
     */
    @Override
    public ResponseResult moneyManage(TradeDto tradeDto, String token) {
        //校验token
        User user = this.tokenCheck(token);
        if (user.getId() == null) {
            return ResponseResult.errorResult(CodeEnum.TOKEN_INVALID);
        }
        String type = tradeDto.getTradeType();
        Long accountId = user.getAccountId();
        Account account = accountMapper.selectById(accountId);
        Trade trade = new Trade();
        //判断交易类型
        if ("1".equals(type)) {
            account.setBalance(account.getBalance() - tradeDto.getTradeMoney());
            trade.setTradeType(1);

        } else if ("2".equals(type)) {
            account.setBalance(account.getBalance() + tradeDto.getTradeMoney());
            trade.setTradeType(2);
        }
        account.setUpdateTime(new Date());
        //往账户表更新数据
        accountMapper.updateById(account);
        trade.setAccountId(accountId);
        trade.setTradeMoney(tradeDto.getTradeMoney());
        trade.setTradeTime(new Date());
        //往流水表添加数据
        tradeMapper.insert(trade);
        return ResponseResult.okResult();
    }

    /**
     * 流水表查询
     * @param token
     * @return
     */
    @Override
    public ResponseResult findTransactionRecord(String token) {
        //校验token
        User user = this.tokenCheck(token);
        if (user.getId() == null) {
            return ResponseResult.errorResult(CodeEnum.TOKEN_INVALID);
        }
        Long accountId = user.getAccountId();
        LambdaQueryWrapper<Trade> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Trade::getAccountId,accountId);
        List<Trade> tradeList = tradeMapper.selectList(queryWrapper);
        return ResponseResult.okResult(tradeList);
    }

    /**
     * 账户表信息查询
     * @param token
     * @return
     */
    @Override
    public ResponseResult findAccountInfo(String token) {
        //校验token
        User user = this.tokenCheck(token);
        if (user.getId() == null) {
            return ResponseResult.errorResult(CodeEnum.TOKEN_INVALID);
        }
        Long accountId = user.getAccountId();
        Account account = accountMapper.selectById(accountId);
        return ResponseResult.okResult(account);
    }


    /**
     * 校验token
     *
     * @param token
     * @return
     */
    private User tokenCheck(String token) {
        System.out.println(token);
//        token= StringUtils.substringAfter(token," ");
        Map<String, Object> bodyMap = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        Long userId = Long.valueOf(bodyMap.get("userId").toString());
        Long accountId = Long.valueOf(bodyMap.get("accountId").toString());
        User user = new User();
        user.setId(userId);
        user.setAccountId(accountId);
        return user;
    }
}
