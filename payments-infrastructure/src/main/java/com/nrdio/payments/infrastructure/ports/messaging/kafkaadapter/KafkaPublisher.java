package com.nrdio.payments.infrastructure.ports.messaging.kafkaadapter;

import com.nrdio.payments.domain.entity.Payment;
import com.nrdio.payments.domain.event.PaymentCreatedEvent;
import com.nrdio.payments.domain.event.PaymentCreatedEventImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class KafkaPublisher {

    private final KafkaTemplate<Object, Payment> kafkaTemplate;

    @Value("${topic.payment}")
    private String kafkaPaymentTopic;

    private void send(Payment payment) {
        this.kafkaTemplate.send(kafkaPaymentTopic, payment);
        log.debug("Published payment creation event");
    }

    @EventListener({PaymentCreatedEventImpl.class})
    public void onPaymentCreation(PaymentCreatedEvent event) {
        log.debug("Received payment creation event {} " + event.getPayment());
        send(event.getPayment());
    }
}
