package com.hixtrip.sample.domain.pay.strategy;

import com.hixtrip.sample.domain.enums.PayStatusEnum;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PayCallbackSuccStrategyService implements PayCallbackService {

    @Autowired
    private OrderDomainService orderDomainService;


    @Override
    public void handle(CommandPay commandPay) {
        orderDomainService.orderPaySuccess(commandPay);
    }

    @Override
    public String isEnum() {
        return PayStatusEnum.SUCCESS.getCode();
    }
}
