package com.kg.web;

import com.kg.QrResource.model.QrCode;
import com.kg.QrResource.service.QrCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class QrResource {
    private final QrCodeGenerator qrCodeGenerator;

    @PostMapping("generate/qr")
    public ResponseEntity<byte[]> generateQr(@RequestBody QrCode qrCode) {
        byte[] link = qrCodeGenerator.generateQrCode(qrCode);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return ResponseEntity.ok().headers(headers).body(link);
    }

    @PostMapping("read/qr")
    public ResponseEntity<String> readQr(@RequestParam("file") byte[] file) {
        String result = qrCodeGenerator.readQRCode(file);
        return ResponseEntity.ok(result);
    }
}
