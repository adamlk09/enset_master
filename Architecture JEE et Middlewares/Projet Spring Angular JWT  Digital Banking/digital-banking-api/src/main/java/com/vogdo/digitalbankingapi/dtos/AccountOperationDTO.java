package com.vogdo.digitalbankingapi.dtos;

import com.vogdo.digitalbankingapi.enums.OperationType;
import lombok.Data;
import java.util.Date;
@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
    private String operatedBy;
}