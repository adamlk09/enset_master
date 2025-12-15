package com.vogdo.digitalbankingapi.dtos;

import lombok.Data;

@Data
public class CreateCurrentAccountRequestDTO {
    private Long customerId;
    private double initialBalance;
    private double overDraft;
}