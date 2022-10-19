package com.lpf.wallet.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_account")
public class Account implements Serializable {
    @TableId
    private Long accountId;
    private Double balance;
    private Date createTime;
    private Date updateTime;
    private String accountName;
}
