package com.hixtrip.sample.client.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付回调的入参
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandPayDTO {

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 支付状态
     */
    //0-- 等待回调 1 -- 成功 2-- 失败 3-- 重复
    private String payStatus;


}
