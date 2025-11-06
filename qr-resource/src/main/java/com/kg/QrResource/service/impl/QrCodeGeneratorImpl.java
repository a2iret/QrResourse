package com.kg.QrResource.service.impl;

import com.google.gson.Gson;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.kg.QrResource.events.publisher.QrCodeEventPublisher;
import com.kg.QrResource.model.QrPayment;
import com.kg.QrResource.service.QrCodeGenerator;
import com.kg.utility.HMAC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class QrCodeGeneratorImpl implements QrCodeGenerator {

    @Value("${payment.qr.url}")
    private String paymentUrl;

    @Value("${secret.HmacSHA256.key}")
    private String secretKey;

    private final QrCodeEventPublisher qrCodeEventPublisher;

    @Override
    public byte[] generateQrCode(QrPayment qrPayment) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            int height = 350;
            int width = 350;

            Map<EncodeHintType, ErrorCorrectionLevel> properties = new HashMap<>();

            // Put the lowest error correction 7%
            properties.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

            // Base64 format
            String jsonData = new Gson().toJson(qrPayment);
            log.info("Json data: {}", jsonData);
            String base64 = Base64.getEncoder().encodeToString(jsonData.getBytes(StandardCharsets.UTF_8));

            // Sign with Hmac
            String signature = generateSignature(qrPayment);

            String url = paymentUrl + "?data=" + base64 + "&signature=" + signature;
            log.info("url: {}", url);
            BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height, properties);

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

            // Publish qr code creation event
            qrCodeEventPublisher.publishQrCodeCreationEvent(qrPayment);

            return bas.toByteArray();

        } catch (WriterException e) {
            log.error("Error while generating QR Code", e);
        } catch (IOException e) {
            log.error("Error writing an image", e);
        }
        return new byte[0];
    }

    private String generateSignature(QrPayment qrPayment) {
        String method = "POST";
        String data = new Gson().toJson(qrPayment);
        String requestedData = String.join(",", method, paymentUrl, data);
        return HMAC.hmacSHA256Digest(requestedData, secretKey);
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
