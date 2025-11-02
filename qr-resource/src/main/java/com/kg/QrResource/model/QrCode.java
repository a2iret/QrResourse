package com.kg.QrResource.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QrCode {
    private Integer size;
    private QrCodeType type;
    private String data;

    private enum QrCodeType {
        STATIC, DYNAMIC
    }
}
