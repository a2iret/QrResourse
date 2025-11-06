package com.kg.QrResource.events.publisher;

import com.kg.QrResource.model.QrPayment;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QrCodeEventPublisher implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(@NonNull ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishQrCodeCreationEvent(QrPayment qrPayment) {
        log.info("Publishing event for QR code creation");
        log.info("qrPayment {}", qrPayment);
        applicationEventPublisher.publishEvent(qrPayment);
    }
}
