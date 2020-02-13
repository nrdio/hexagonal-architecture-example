package com.nrdio.payments.infrastructure.service;

import com.nrdio.payments.domain.event.PaymentCreatedEvent;
import com.nrdio.payments.domain.service.PaymentEventNotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentEventNotificationServiceImpl implements PaymentEventNotificationService {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void publishPaymentCreatedEvent(PaymentCreatedEvent payment) {
        eventPublisher.publishEvent(payment);
    }
}
