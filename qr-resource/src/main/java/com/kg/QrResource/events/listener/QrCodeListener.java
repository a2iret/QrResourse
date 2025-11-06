package com.kg.QrResource.events.listener;

import com.kg.QrResource.model.QrPayment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QrCodeListener {

    @EventListener
    @Async
    public void qrPaymentCreationEvent(QrPayment qrPayment) {
        log.info("Qr Payment created event received");
        log.info("Qr Payment Entity: {}", qrPayment);
    }
}
