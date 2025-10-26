package com.example.QrResource.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class QrController {

    @GetMapping("generate/qr")
    public String generateQr(){
        return "";
    }
}
