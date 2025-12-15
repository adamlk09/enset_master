package com.vogdo.digitalbankingapi.services;

import com.vogdo.digitalbankingapi.dtos.*;
import com.vogdo.digitalbankingapi.exceptions.BalanceNotSufficientException;
import com.vogdo.digitalbankingapi.exceptions.BankAccountNotFoundException;
import com.vogdo.digitalbankingapi.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(CreditDTO creditDTO) throws BankAccountNotFoundException;
    void transfer(TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException;
    List<BankAccountDTO> bankAccountList();
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(Long customerId);
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}