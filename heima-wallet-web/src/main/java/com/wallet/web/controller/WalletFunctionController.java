package com.wallet.web.controller;

import com.lpf.wallet.controller.WalletFunctionControllerApi;
import com.lpf.wallet.dto.TradeDto;
import com.lpf.wallet.service.WalletFunctionService;
import com.lpf.wallet.vo.ResponseResult;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet/v1")
public class WalletFunctionController implements WalletFunctionControllerApi {
    @Reference
    private WalletFunctionService walletFunctionService;
    @Override
    @PostMapping("/moneyManage")
    public ResponseResult moneyManage(@RequestBody TradeDto tradeDto, @RequestHeader("Authorization") String token) {
        return this.walletFunctionService.moneyManage(tradeDto,token);
    }

    @Override
    @GetMapping("/findTrade")
    public ResponseResult findTransactionRecord(@RequestHeader("Authorization")String token) {
        return this.walletFunctionService.findTransactionRecord(token);
    }

    @Override
    @GetMapping("/findAccount")
    public ResponseResult findAccountInfo(@RequestHeader("Authorization")String token) {
        return this.walletFunctionService.findAccountInfo(token);
    }
}
