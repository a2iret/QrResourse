package com.kg.QrResource.service;

import com.kg.QrResource.model.QrPayment;

public interface QrCodeGenerator {
    byte[] generateQrCode(QrPayment qrPayment);

    String readQRCode(byte[] imageBytes);
}
