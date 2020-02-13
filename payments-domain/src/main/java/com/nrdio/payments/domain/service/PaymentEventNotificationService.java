package com.nrdio.payments.domain.service;


import com.nrdio.payments.domain.event.PaymentCreatedEvent;

public interface PaymentEventNotificationService {

    void publishPaymentCreatedEvent(PaymentCreatedEvent payment);
}
