package com.kg.QrResource.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class Payment {
    private QrCodeType type;
    private Boolean isSumEditable;
    private BigDecimal sum;

    private enum QrCodeType {
        STATIC, DYNAMIC
    }
}
