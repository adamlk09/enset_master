package com.vogdo.digitalbankingapi.dtos;

import com.vogdo.digitalbankingapi.enums.AccountStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class SavingAccountDTO extends BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;
}