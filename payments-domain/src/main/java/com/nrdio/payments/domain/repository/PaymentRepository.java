package com.nrdio.payments.domain.repository;

import com.nrdio.payments.domain.entity.Payment;

import java.util.Optional;

public interface PaymentRepository {

    Payment savePayment(Payment payment);

    Optional<Payment> findPaymentById(String paymentId);

    Optional<Payment> getPaymentByInstructionIdAndEndToEndId(String instructionId, String endToEndId);

}
