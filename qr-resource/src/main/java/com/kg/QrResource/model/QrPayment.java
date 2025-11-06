package com.kg.QrResource.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Data
public class QrPayment {
    private QrCodeType type;
    private Boolean isSumEditable;
    private BigDecimal sum;

    @Builder.Default
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final String qrTransactionId = String.valueOf(System.currentTimeMillis());

    @Builder.Default
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private final String createdTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .format(LocalDateTime.now());

    private enum QrCodeType {
        STATIC, DYNAMIC
    }
}
