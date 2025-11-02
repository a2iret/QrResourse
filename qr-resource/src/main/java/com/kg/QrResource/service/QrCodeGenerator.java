package com.kg.QrResource.service;

import com.kg.QrResource.model.QrCode;

public interface QrCodeGenerator {
    byte[] generateQrCode(QrCode qrCode);

    String readQRCode(byte[] imageBytes);
}
