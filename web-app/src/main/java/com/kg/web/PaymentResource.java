package com.kg.web;

import com.kg.annotations.ValidateHmacSHA256;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@Slf4j
@RestController
@RequestMapping("api/payments")
@RequiredArgsConstructor
public class PaymentResource {

    @GetMapping("pay")
    @ValidateHmacSHA256
    public ResponseEntity<String> pay(@RequestParam(name = "data") String data,
                                      @RequestParam(name = "signature") String signature) {
        Base64.Decoder decoder = Base64.getDecoder();
        String decoded = new String(decoder.decode(data));
        log.info("Payment received: {}", decoded);
        log.info("Payment signature: {}", signature);
        return new ResponseEntity<>(decoded, HttpStatus.OK);
    }
}
