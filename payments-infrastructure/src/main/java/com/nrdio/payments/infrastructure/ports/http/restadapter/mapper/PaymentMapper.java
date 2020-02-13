package com.nrdio.payments.infrastructure.ports.http.restadapter.mapper;

import com.nrdio.payments.domain.entity.Payment;
import com.nrdio.payments.infrastructure.rest.model.PaymentSetupInitiation;
import com.nrdio.payments.infrastructure.rest.model.PaymentSetupPOSTRequest;
import com.nrdio.payments.infrastructure.rest.model.PaymentSetupPOSTResponse;
import com.nrdio.payments.infrastructure.rest.model.PaymentSetupResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;


@Component
public class PaymentMapper {

    private ModelMapper paymentRequestToPaymentModel;
    private ModelMapper paymentToPaymentResponseModel;

    public PaymentMapper() {
        initPaymentRequestToPaymentModel();
        initPaymentToPaymentResponseModel();
    }

    public Payment mapPaymentRequestToPayment(PaymentSetupPOSTRequest paymentRequest) {
        Payment payment = this.paymentRequestToPaymentModel.map(paymentRequest.getData().getInitiation(), Payment.class);
        return payment;
    }

    public com.nrdio.payments.infrastructure.rest.model.PaymentSetupPOSTResponse mapPaymentToPaymentResponse(Payment request) {
        return this.paymentToPaymentResponseModel.map(request, PaymentSetupPOSTResponse.class);
    }

    private void initPaymentToPaymentResponseModel() {
        this.paymentToPaymentResponseModel = new ModelMapper();
        this.paymentToPaymentResponseModel.addMappings(new PropertyMap<com.nrdio.payments.infrastructure.ports.persistance.mongoadapter.entity.Payment, PaymentSetupPOSTResponse>() {
            @Override
            protected void configure() {
                map().getData().setPaymentId(source.getId());
                map().getData().getInitiation().setEndToEndIdentification(source.getEndToEndIdentification());
                map().getData().getInitiation().setInstructionIdentification(source.getInstructionIdentification());

                map().getData().getInitiation().getDebtorAccount().setIdentification(source.getDebtorAccount().getIban());
                map().getData().getInitiation().getDebtorAccount().setName(source.getDebtorAccount().getName());
                map().getData().getInitiation().getDebtorAccount().setSchemeName(com.nrdio.payments.infrastructure.rest.model.DebtorAccount.SchemeNameEnum.IBAN);

                map().getData().getInitiation().getCreditorAccount().setSchemeName(com.nrdio.payments.infrastructure.rest.model.CreditorAccount.SchemeNameEnum.IBAN);
                map().getData().getInitiation().getCreditorAccount().setIdentification(source.getCreditorAccount().getIban());
                map().getData().getInitiation().getCreditorAccount().setName(source.getCreditorAccount().getName());

                map().getData().getInitiation().getInstructedAmount().setCurrency(source.getCurrency());
                map().getData().getInitiation().getInstructedAmount().setAmount(String.valueOf(source.getAmount()));

                map().getData().getInitiation().getRemittanceInformation().setUnstructured(source.getRemittanceInformation());

                using((MappingContext<Payment.Status, PaymentSetupResponse.StatusEnum> ctx) ->
                        ctx.getSource() == null ? null : PaymentSetupResponse.StatusEnum.valueOf(ctx.getSource().name()))
                        .map(source.getStatus()).getData().setStatus(null);
            }
        });
    }

    private void initPaymentRequestToPaymentModel() {
        this.paymentRequestToPaymentModel = new ModelMapper();
        this.paymentRequestToPaymentModel.addMappings(new PropertyMap<PaymentSetupInitiation, com.nrdio.payments.infrastructure.ports.persistance.mongoadapter.entity.Payment>() {
            @Override
            protected void configure() {
                map().getCreditorAccount().setIban(source.getCreditorAccount().getIdentification());
                map().getDebtorAccount().setIban(source.getDebtorAccount().getIdentification());
                map().setCurrency(source.getInstructedAmount().getCurrency());
                map().setRemittanceInformation(source.getRemittanceInformation().getUnstructured());
            }
        });
    }
}
