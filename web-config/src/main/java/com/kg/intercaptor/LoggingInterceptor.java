package com.kg.intercaptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        MDC.put("qrTransactionId", String.valueOf(System.currentTimeMillis()));
        request.setAttribute("startTime", LocalDateTime.now());

        log.info("{} Request {} {}", MDC.get("qrTransactionId"), request.getMethod(), request.getRequestURI());

        if (request instanceof ContentCachingRequestWrapper requestWrapper){
            String requestBody = new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
            if (!requestBody.isBlank())
             log.info("📨 Request body: {}", requestBody);
        }
        return true;
    }

    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, ModelAndView modelAndView) throws Exception {
        log.info("Controller finished processing: {}", request.getRequestURI());
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, Exception ex) throws Exception {
        LocalDateTime startTime = (LocalDateTime) request.getAttribute("startTime");
        LocalDateTime endTime = LocalDateTime.now();

        Duration duration = Duration.between(startTime, endTime);

        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();

        if (response instanceof ContentCachingResponseWrapper responseWrapper){
            String responseBody = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
            log.info("Response body: {}", responseBody);
        }
        if (ex != null){
            log.error("{} ❌ Exception in {} {} processed in {} min {} sec",
                         MDC.get("qrTransactionId"), request.getMethod(), request.getRequestURI(),
                    minutes, seconds, ex);
        }else {
            log.info("{} ✅ {} {} completed in {} min {} sec, status={}",
                    MDC.get("qrTransactionId"),
                    request.getMethod(), request.getRequestURI(),
                    minutes, seconds, response.getStatus());
        }
        MDC.remove("qrTransactionId");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
