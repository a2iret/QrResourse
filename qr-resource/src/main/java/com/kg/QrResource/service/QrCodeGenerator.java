package com.kg.QrResource.service;

import com.kg.QrResource.model.Payment;

public interface QrCodeGenerator {
    byte[] generateQrCode(Payment payment);

    String readQRCode(byte[] imageBytes);
}
