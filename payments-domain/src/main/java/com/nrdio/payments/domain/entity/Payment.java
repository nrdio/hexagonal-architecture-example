package com.nrdio.payments.domain.entity;


import com.nrdio.payments.domain.event.PaymentCreatedEventImpl;
import com.nrdio.payments.domain.repository.PaymentRepository;
import com.nrdio.payments.domain.service.PaymentEventNotificationService;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

@Getter
@Builder
public class Payment {

    String id;

    String instructionIdentification;

    String endToEndIdentification;

    Account debtorAccount;

    Account creditorAccount;

    Status status;

    BigDecimal amount;

    String currency;

    String remittanceInformation;

    String idempotencyKey;

    ZonedDateTime creationDateTime;

    ZonedDateTime lastModificationDate;

    private PaymentRepository repository;

    private PaymentEventNotificationService notificationService;


    public void createPayment() {
        Optional<Payment> existingPayment = repository.getPaymentByInstructionIdAndEndToEndId(this.instructionIdentification, this.endToEndIdentification);

        existingPayment.orElseGet(() -> {
            status = Payment.Status.ACCEPTEDCUSTOMERPROFILE;
            Payment storedPayment = repository.savePayment(this);
            notificationService.publishPaymentCreatedEvent(new PaymentCreatedEventImpl(storedPayment));
            return storedPayment;
        });
    }

    public Optional<Payment> getPayment(String id) {
        return repository.findPaymentById(id);
    }

    public enum Status {

        ACCEPTEDCUSTOMERPROFILE("AcceptedCustomerProfile"),

        ACCEPTEDTECHNICALVALIDATION("AcceptedTechnicalValidation"),

        PENDING("Pending"),

        REJECTED("Rejected");

        private String value;

        Status(String value) {
            this.value = value;
        }

        public static Status fromValue(String text) {
            for (Status b : Status.values()) {
                if (String.valueOf(b.value).equals(text)) {
                    return b;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }
}
