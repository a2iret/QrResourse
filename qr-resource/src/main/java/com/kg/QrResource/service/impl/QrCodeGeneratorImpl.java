package com.kg.QrResource.service.impl;

import com.kg.QrResource.service.QrCodeGenerator;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@Service
public class QrCodeGeneratorImpl implements QrCodeGenerator {

    @Override
    public byte[] generateQrCode(String link) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            int height = 350;
            int width = 350;

            BitMatrix bitMatrix = qrCodeWriter.encode(link, BarcodeFormat.QR_CODE, width, height);

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = bufferedImage.createGraphics();
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(Color.BLACK);

            for (int x = 0; x < 350; x++) {
                for (int y = 0; y < 350; y++) {
                    if (bitMatrix.get(x, y)) {
                        graphics.fillRect(x, y, 1, 1);
                    }
                }
            }
            ByteArrayOutputStream bas = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", bas);
            return bas.toByteArray();

        } catch (WriterException e) {
            log.error("Error while generating QR Code", e);
        } catch (IOException e) {
            log.error("Error writing an image", e);
        }
        return new byte[0];
    }

    @Override
    public String readQRCode(byte[] imageBytes) {
        try {
            ByteArrayInputStream bass = new ByteArrayInputStream(imageBytes);
            BufferedImage bufferedImage = ImageIO.read(bass);

            BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            HybridBinarizer hybridBinarizer = new HybridBinarizer(source);
            com.google.zxing.BinaryBitmap bitmap = new com.google.zxing.BinaryBitmap(hybridBinarizer);
            MultiFormatReader reader = new MultiFormatReader();

            Result result = reader.decode(bitmap);
            return result.getText();
        } catch (ReaderException | IOException e) {
            return "Error reading QR code";
        }
    }
}
