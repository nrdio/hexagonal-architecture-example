package com.nrdio.payments.domain.event;

import com.nrdio.payments.domain.entity.Payment;

public class PaymentCreatedEventImpl implements PaymentCreatedEvent {

    private Payment payment;

    public PaymentCreatedEventImpl(Payment payment) {
        this.payment = payment;
    }

    @Override
    public Payment getPayment() {
        return payment;
    }
}
