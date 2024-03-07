package com.hixtrip.sample.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatusEnum  {


    WAIT("0","等待支付"),
    SUCCESS("1","支付成功"),
    FAIL("2","支付失败"),
    REPEAT("3","重复成功");

    private String code;

    private String desc;
}
