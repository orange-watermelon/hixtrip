package com.hixtrip.sample.app.service;

import com.hixtrip.sample.app.api.OrderService;
import com.hixtrip.sample.app.convertor.OrderConvertor;
import com.hixtrip.sample.client.order.dto.CommandOderCreateDTO;
import com.hixtrip.sample.client.order.dto.CommandPayDTO;
import com.hixtrip.sample.domain.commodity.CommodityDomainService;
import com.hixtrip.sample.domain.enums.PayStatusEnum;
import com.hixtrip.sample.domain.inventory.InventoryDomainService;
import com.hixtrip.sample.domain.inventory.repository.InventoryRepository;
import com.hixtrip.sample.domain.order.OrderDomainService;
import com.hixtrip.sample.domain.order.model.Order;
import com.hixtrip.sample.domain.pay.PayDomainService;
import com.hixtrip.sample.domain.pay.model.CommandPay;
import com.hixtrip.sample.infra.db.dataobject.OrderDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * app层负责处理request请求，调用领域服务
 */
@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CommodityDomainService commodityDomainService;
    @Autowired
    private InventoryDomainService inventoryDomainService;
    @Autowired
    private PayDomainService payDomainService;
    @Autowired
    private OrderDomainService orderDomainService;

    @Override
    public void order(CommandOderCreateDTO commandOderCreateDTO) {
        // 查询SKU价格
        BigDecimal skuPrice = commodityDomainService.getSkuPrice(commandOderCreateDTO.getSkuId());
        // 扣减库存
        Integer inventory = inventoryDomainService.getInventory(commandOderCreateDTO.getSkuId());
        Boolean result = inventoryDomainService.changeInventory(commandOderCreateDTO.getSkuId(), new Long(inventory), new Long(commandOderCreateDTO.getAmount()), new Long(commandOderCreateDTO.getAmount()));
        if (result == false){
            return;
        }
        // 支付
        payDomainService.payRecord(new CommandPay());
        // 生成订单

        Order order = Order.builder().amount(commandOderCreateDTO.getAmount())
                .createBy(commandOderCreateDTO.getUserId())
                .createTime(LocalDateTime.now())
                .money(skuPrice.multiply(new BigDecimal(commandOderCreateDTO.getAmount())))
                .delFlag(0l)
                .payStatus(PayStatusEnum.WAIT.getCode())
                .payTime(LocalDateTime.now())
                .skuId(commandOderCreateDTO.getSkuId())
                .updateBy(commandOderCreateDTO.getSkuId())
                .updateTime(LocalDateTime.now()).build();
        orderDomainService.createOrder(order);
    }


    @Override
    public String payCallback(CommandPayDTO commandPayDTO){
        Order order = new Order();
        Boolean exist = order.existOrder(commandPayDTO.getOrderId());
        if(exist) {
            commandPayDTO.setPayStatus(PayStatusEnum.REPEAT.getCode());
        }
        payDomainService.callBack(OrderConvertor.INSTANCE.convert(commandPayDTO));
        return "true";
    }
}
