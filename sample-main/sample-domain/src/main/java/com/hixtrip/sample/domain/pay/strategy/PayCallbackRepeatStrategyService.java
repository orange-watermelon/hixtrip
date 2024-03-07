package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.enums.PayStatusEnum;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.stereotype.Component;

@Component
public class PayCallbackRepeatStrategyService implements PayCallbackService {
    @Override
    public void handle(CommandPay commandPay) {

    }

    @Override
    public  String isEnum() {
        return PayStatusEnum.REPEAT.getCode();
    }
}
