package com.nrdio.payments.infrastructure.ports.persistance.mongoadapter.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Document(collection = "payments")
@CompoundIndexes({
        @CompoundIndex(
                unique = true,
                background = true,
                name = "instructionId_endToEndId_unique_key",
                def = "{'instructionIdentification': 1, 'endToEndIdentification' : 1}")
})
public class Payment {

    @Id
    private String id;

    private String instructionIdentification;

    private String endToEndIdentification;

    private Account debtorAccount;

    private Account creditorAccount;

    private com.nrdio.payments.domain.entity.Payment.Status status;

    private BigDecimal amount;

    private String currency;

    private String remittanceInformation;

    private String idempotencyKey;

    @CreatedDate
    private ZonedDateTime creationDateTime;

    @LastModifiedDate
    private ZonedDateTime lastModificationDate;

}
