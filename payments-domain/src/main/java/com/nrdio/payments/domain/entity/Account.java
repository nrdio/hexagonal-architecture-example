package com.nrdio.payments.domain.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Account {

    String iban;

    String name;

}
