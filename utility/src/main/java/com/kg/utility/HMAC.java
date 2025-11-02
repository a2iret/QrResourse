package com.kg.utility;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
@UtilityClass
public class HMAC {
    public static String hmacSHA256Digest(String message, String secret) {
        try {
            Mac sha256HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256HMAC.init(secretKey);
            return Base64.getEncoder().encodeToString(sha256HMAC.doFinal(message.getBytes()));
        } catch (Exception ex) {
            log.error("HmacSHA256 exception", ex);
            return "ERROR";
        }
    }
}
