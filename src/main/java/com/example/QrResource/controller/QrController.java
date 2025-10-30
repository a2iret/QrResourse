package com.example.QrResource.controller;

import com.example.QrResource.service.QrCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class QrController {
    private final QrCodeGenerator qrCodeGenerator;

    @PostMapping("generate/qr")
    public ResponseEntity<byte[]> generateQr(){
        byte[] qrCode = qrCodeGenerator.generateQrCode("https://www.google.com");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return ResponseEntity.ok().headers(headers).body(qrCode);
    }
}
