package com.kg.QrResource.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Data
public class Payment {
    private QrCodeType type;
    private Boolean isSumEditable;
    private BigDecimal sum;

    @Builder.Default
    private final String qrTransactionId = String.valueOf(System.currentTimeMillis());

    @Builder.Default
    private final String createdTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .format(LocalDateTime.now());

    private enum QrCodeType {
        STATIC, DYNAMIC
    }
}
