package com.lpf.wallet.service;

import com.lpf.wallet.dto.TradeDto;
import com.lpf.wallet.vo.ResponseResult;

public interface WalletFunctionService {
    /**
     * 资金管理，充值，提现等
     * @param tradeDto
     * @return
     */
    public ResponseResult moneyManage(TradeDto tradeDto, String token);

    /**
     * 交易记录,流水表查询
     * @param token
     * @return
     */
    public  ResponseResult  findTransactionRecord(String token);

    /**
     * 账户信息查询
     * @param token
     * @return
     */

    public  ResponseResult  findAccountInfo(String token);

//    /**
//     * 校验token
//     * @param token
//     * @return
//     */
//    public boolean tokenCheck(String token);
}
