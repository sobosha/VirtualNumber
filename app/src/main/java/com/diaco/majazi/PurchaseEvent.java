package com.diaco.majazi;


import com.diaco.majazi.Core.MarketResult;

public interface PurchaseEvent {
    void NormalPay();
    void SuccessPay(MarketResult result);
    void ErrorPay();
}
