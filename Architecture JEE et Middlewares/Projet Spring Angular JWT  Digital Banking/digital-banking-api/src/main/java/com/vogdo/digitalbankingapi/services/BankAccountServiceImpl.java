package com.vogdo.digitalbankingapi.services;

import com.vogdo.digitalbankingapi.dtos.*;
import com.vogdo.digitalbankingapi.entities.*;
import com.vogdo.digitalbankingapi.enums.AccountStatus;
import com.vogdo.digitalbankingapi.enums.OperationType;
import com.vogdo.digitalbankingapi.exceptions.BalanceNotSufficientException;
import com.vogdo.digitalbankingapi.exceptions.BankAccountNotFoundException;
import com.vogdo.digitalbankingapi.exceptions.CustomerNotFoundException;
import com.vogdo.digitalbankingapi.mappers.BankAccountMapper;
import com.vogdo.digitalbankingapi.repositories.AccountOperationRepository;
import com.vogdo.digitalbankingapi.repositories.BankAccountRepository;
import com.vogdo.digitalbankingapi.repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapper dtoMapper;

    private String getAuthenticatedUser() {
        // Pour l'instant, avant d'implémenter le login, on retourne une valeur par défaut.
        // Plus tard, ce sera : return SecurityContextHolder.getContext().getAuthentication().getName();
        return "system.user";
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public CurrentAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        currentAccount.setStatus(AccountStatus.CREATED);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }

    @Override
    public SavingAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        savingAccount.setStatus(AccountStatus.CREATED);
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingBankAccount(savedBankAccount);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        // Utilisation d'un stream pour convertir la liste d'entités en liste de DTOs
        return customers.stream()
                .map(dtoMapper::fromCustomer)
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("BankAccount not found"));
        if (bankAccount instanceof SavingAccount) {
            return dtoMapper.fromSavingBankAccount((SavingAccount) bankAccount);
        } else {
            return dtoMapper.fromCurrentBankAccount((CurrentAccount) bankAccount);
        }
    }

    @Override
    public void debit(DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = bankAccountRepository.findById(debitDTO.getAccountId())
                .orElseThrow(() -> new BankAccountNotFoundException("BankAccount not found"));
        if (bankAccount.getBalance() < debitDTO.getAmount())
            throw new BalanceNotSufficientException("Balance not sufficient");

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(debitDTO.getAmount());
        accountOperation.setDescription(debitDTO.getDescription());
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setOperatedBy(getAuthenticatedUser());
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - debitDTO.getAmount());
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(CreditDTO creditDTO) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(creditDTO.getAccountId())
                .orElseThrow(() -> new BankAccountNotFoundException("BankAccount not found"));

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(creditDTO.getAmount());
        accountOperation.setDescription(creditDTO.getDescription());
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setOperatedBy(getAuthenticatedUser());
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() + creditDTO.getAmount());
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(new DebitDTO(transferRequestDTO.getAccountSource(), transferRequestDTO.getAmount(), transferRequestDTO.getDescription()));
        credit(new CreditDTO(transferRequestDTO.getAccountDestination(), transferRequestDTO.getAmount(), transferRequestDTO.getDescription()));
    }

    @Override
    public List<BankAccountDTO> bankAccountList() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount) {
                return dtoMapper.fromSavingBankAccount((SavingAccount) bankAccount);
            } else {
                return dtoMapper.fromCurrentBankAccount((CurrentAccount) bankAccount);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return dtoMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Updating Customer");
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new BankAccountNotFoundException("BankAccount Not Found"));

        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountId(accountId, PageRequest.of(page, size));

        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        List<AccountOperationDTO> operationDTOS = accountOperations.getContent().stream()
                .map(op -> dtoMapper.fromAccountOperation(op))
                .collect(Collectors.toList());

        accountHistoryDTO.setAccountOperationDTOS(operationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());

        return accountHistoryDTO;
    }
}