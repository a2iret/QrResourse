package com.kg.QrResource.service;

import java.io.IOException;

public interface QrCodeGenerator {
    byte[] generateQrCode(String link);

    String readQRCode(byte[] imageBytes) throws IOException;
}
