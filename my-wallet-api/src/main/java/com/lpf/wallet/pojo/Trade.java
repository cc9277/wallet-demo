package com.lpf.wallet.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_trade")
public class Trade implements Serializable {

    @TableId(value = "trade_id",type = IdType.AUTO)
    private Integer tradeId;
    private Date tradeTime;
    private Double tradeMoney;
    private Integer tradeType;
    private Long accountId;
}
