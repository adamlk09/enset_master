package com.vogdo.digitalbankingapi.mappers;

import com.vogdo.digitalbankingapi.dtos.AccountOperationDTO;
import com.vogdo.digitalbankingapi.dtos.CurrentAccountDTO;
import com.vogdo.digitalbankingapi.dtos.CustomerDTO;
import com.vogdo.digitalbankingapi.dtos.SavingAccountDTO;
import com.vogdo.digitalbankingapi.entities.AccountOperation;
import com.vogdo.digitalbankingapi.entities.CurrentAccount;
import com.vogdo.digitalbankingapi.entities.Customer;
import com.vogdo.digitalbankingapi.entities.SavingAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    CustomerDTO fromCustomer(Customer customer);
    Customer fromCustomerDTO(CustomerDTO customerDTO);

    SavingAccountDTO fromSavingBankAccount(SavingAccount savingAccount);
    SavingAccount fromSavingBankAccountDTO(SavingAccountDTO savingAccountDTO);

    CurrentAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount);
    CurrentAccount fromCurrentBankAccountDTO(CurrentAccountDTO currentAccountDTO);

    AccountOperationDTO fromAccountOperation(AccountOperation accountOperation);
}