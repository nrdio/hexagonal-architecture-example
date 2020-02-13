package com.nrdio.payments.infrastructure.ports.persistance.mongoadapter.repository;

import com.nrdio.payments.domain.entity.Payment;
import com.nrdio.payments.domain.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PaymentMongoRepository implements PaymentRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Payment savePayment(Payment payment) {
        mongoTemplate.save(payment);
        return payment;
    }

    @Override
    public Optional<Payment> findPaymentById(String paymentId) {
        return Optional.of(mongoTemplate.findById(paymentId, Payment.class, "payments"));
    }

    @Override
    public Optional<Payment> getPaymentByInstructionIdAndEndToEndId(String instructionId, String endToEndId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("instructionIdentification").is(instructionId)
                .and("endToEndIdentification").is(endToEndId));

        return Optional.ofNullable(mongoTemplate.findOne(query, Payment.class, "payments"));
    }
}
