package com.wallet.web.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TradeDto implements Serializable {
    private String tradeMoney;
    private String tradeType;
}
