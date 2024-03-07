package com.hixtrip.sample.domain.pay.model;

import com.hixtrip.sample.domain.pay.strategy.PayCallbackService;
import com.hixtrip.sample.domain.util.SpringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommandPay {

    private Map<String, PayCallbackService> map;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 订单状态
     */
    private String payStatus;

    public PayCallbackService getPayBean(){
        if (map == null) {
            synchronized (this){
                if (map == null) {
                    map =new HashMap<>();
                    Map<String, PayCallbackService> beansOfType = SpringUtils.getBeansOfType(PayCallbackService.class);
                    beansOfType.values().forEach(t->{
                        map.put(t.isEnum(), t);
                    });
                }
            }
        }
        return map.get(this.payStatus);
    }
}