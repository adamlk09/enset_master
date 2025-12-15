package com.vogdo.digitalbankingapi.dtos;

import lombok.Data;

@Data
public class CreateSavingAccountRequestDTO {
    private Long customerId;
    private double initialBalance;
    private double interestRate;
}