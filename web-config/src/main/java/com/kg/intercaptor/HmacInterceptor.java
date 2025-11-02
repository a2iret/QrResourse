package com.kg.intercaptor;

import com.kg.annotations.ValidateHmacSHA256;
import com.kg.utility.HMAC;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class HmacInterceptor implements HandlerInterceptor {

    @Value("${secret.HmacSHA256.key}")
    private String secretKey;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        if (handler instanceof HandlerMethod method) {
            if (method.hasMethodAnnotation(ValidateHmacSHA256.class)) {
                String data = request.getParameter("data");
                String signature = request.getParameter("signature");

                if (StringUtils.isBlank(data) && StringUtils.isBlank(signature)) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return false;
                }

                String jsonData = new String(Base64.getDecoder().decode(data), StandardCharsets.UTF_8);
                String generatedSignature = HMAC.hmacSHA256Digest(jsonData, secretKey);
                if (!generatedSignature.equals(signature)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    return false;
                }
            }
        }
        return true;
    }
}
