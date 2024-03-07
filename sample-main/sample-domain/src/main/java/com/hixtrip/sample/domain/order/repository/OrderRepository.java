package com.hixtrip.sample.domain.order.repository;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.model.CommandPay;

/**
 *
 */
public interface OrderRepository {
    void create(Order order);

    Boolean existOrderId(String orderId);

    Boolean updateOrderStatus(CommandPay commandPay);
}
