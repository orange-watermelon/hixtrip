package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.pay.model.CommandPay;

public interface PayCallbackService {

     void handle(CommandPay commandPay);

     String isEnum();
}
