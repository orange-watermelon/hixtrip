package com.hixtrip.sample.infra;

import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.order.repository.OrderRepository;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.convertor.OrderDOConvertor;
import com.hixtrip.sample.infra.db.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OrderRepositoryImpl implements OrderRepository {
    @Autowired
    private OrderMapper orderMapper;


    @Override
    public void create(Order order) {
        orderMapper.insert(OrderDOConvertor.INSTANCE.domainToDO(order));
    }


    @Override
    public Boolean existOrderId(String orderId) {
        return orderMapper.selectById(orderId) == null ? true : false;
      /*  return orderMapper.exists(new QueryWrapper<OrderDO>().eq(OrderDO::getId, orderId));*/
    }

    @Override
    public Boolean updateOrderStatus(CommandPay commandPay) {

        Order order = new Order();
        order.setUpdateTime(LocalDateTime.now());
        order.setPayStatus(commandPay.getPayStatus());
        order.setId(commandPay.getOrderId());
        return orderMapper.updateStatus(order) > 0 ? true : false;
        /*
        return orderMapper.update(null, new UpdateWrapper<OrderDO>().set(OrderDO::getPayStatus, commandPay.getPayStatus())
        .set(OrderDO::getUpdateTime, LocalDateTime.now())
        .eq(OrderDO::getId, commandPay.getOrderId()));*/
    }
}
