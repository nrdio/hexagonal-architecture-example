package com.nrdio.payments.domain.event;

import com.nrdio.payments.domain.entity.Payment;

public interface PaymentCreatedEvent {

    Payment getPayment();

}
