package com.nrdio.payments.infrastructure.service;

import com.nrdio.payments.domain.entity.Payment;
import com.nrdio.payments.domain.repository.PaymentRepository;
import com.nrdio.payments.infrastructure.rest.model.PaymentSetupPOSTRequest;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentApplicationService {

    private final PaymentRepository mongoRepository;
    private final PaymentEventNotificationServiceImpl notificationService;

    public Payment createPayment(PaymentSetupPOSTRequest paymentRequest) {
//        PaymentServiceImpl(mongoRepository, notificationService);
//        return paymentService.createPayment(payment);
//        Payment payment = Payment.builder().
        return null;
    }

    public Optional<Payment> getPayment(String id) {
//        PaymentService paymentService = new PaymentServiceImpl(mongoRepository, notificationService);
//        return paymentService.getPayment(id);
        return Optional.empty();
    }
}
