package com.lpf.wallet.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TradeDto implements Serializable {
    private Double tradeMoney;
    private String tradeType;
}
