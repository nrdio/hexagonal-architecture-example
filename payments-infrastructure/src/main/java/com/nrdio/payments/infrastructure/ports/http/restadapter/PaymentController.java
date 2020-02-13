package com.nrdio.payments.infrastructure.ports.http.restadapter;

import com.nrdio.payments.domain.entity.Payment;
import com.nrdio.payments.infrastructure.ports.http.restadapter.mapper.PaymentMapper;
import com.nrdio.payments.infrastructure.rest.api.PaymentsApi;
import com.nrdio.payments.infrastructure.rest.model.PaymentSetupPOSTRequest;
import com.nrdio.payments.infrastructure.rest.model.PaymentSetupPOSTResponse;
import com.nrdio.payments.infrastructure.service.PaymentApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/v1")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentController implements PaymentsApi {

    private final PaymentApplicationService paymentApplicationService;
    private final PaymentMapper paymentMapper;

    @Override
    public ResponseEntity<PaymentSetupPOSTResponse> createSingleImmediatePayment(@RequestHeader(value = "x-idempotency-key", required = true) String xIdempotencyKey,
                                                                                 @RequestHeader(value = "x-fapi-financial-id", required = true) String xFapiFinancialId,
                                                                                 @RequestHeader(value = "Authorization", required = true) String authorization,
                                                                                 @Valid @RequestBody PaymentSetupPOSTRequest body,
                                                                                 @RequestHeader(value = "x-fapi-customer-last-logged-time", required = false) String xFapiCustomerLastLoggedTime,
                                                                                 @RequestHeader(value = "x-fapi-customer-ip-address", required = false) String xFapiCustomerIpAddress,
                                                                                 @RequestHeader(value = "x-fapi-interaction-id", required = false) String xFapiInteractionId,
                                                                                 @RequestHeader(value = "x-jws-signature", required = false) String xJwsSignature) {
        Payment payment = paymentApplicationService.createPayment(body);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentMapper.mapPaymentToPaymentResponse(payment));
    }

    @Override
    public ResponseEntity<PaymentSetupPOSTResponse> getSingleImmediatePayment(@PathVariable("PaymentId") String paymentId,
                                                                              @RequestHeader(value = "x-fapi-financial-id", required = true) String xFapiFinancialId,
                                                                              @RequestHeader(value = "Authorization", required = true) String authorization,
                                                                              @RequestHeader(value = "x-fapi-customer-last-logged-time", required = false) String xFapiCustomerLastLoggedTime,
                                                                              @RequestHeader(value = "x-fapi-customer-ip-address", required = false) String xFapiCustomerIpAddress,
                                                                              @RequestHeader(value = "x-fapi-interaction-id", required = false) String xFapiInteractionId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(paymentApplicationService.getPayment(paymentId)
                        .map(paymentMapper::mapPaymentToPaymentResponse)
                        .orElseThrow(() -> new NoSuchElementException()));
    }
}
