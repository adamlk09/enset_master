package com.vogdo.digitalbankingapi.controllers;

import com.vogdo.digitalbankingapi.dtos.*;
import com.vogdo.digitalbankingapi.exceptions.BalanceNotSufficientException;
import com.vogdo.digitalbankingapi.exceptions.BankAccountNotFoundException;
import com.vogdo.digitalbankingapi.exceptions.CustomerNotFoundException;
import com.vogdo.digitalbankingapi.services.BankAccountService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api/accounts")
@CrossOrigin("*") // Optionnel : Utile pour le développement avec Angular
@SecurityRequirement(name = "bearerAuth") // Protégé par JWT
public class BankAccountRestController {
    private BankAccountService bankAccountService;

    @PostMapping("/current")
    public CurrentAccountDTO saveCurrentAccount(@RequestBody CreateCurrentAccountRequestDTO request) throws CustomerNotFoundException {
        return bankAccountService.saveCurrentBankAccount(request.getInitialBalance(), request.getOverDraft(), request.getCustomerId());
    }

    @PostMapping("/saving")
    public SavingAccountDTO saveSavingAccount(@RequestBody CreateSavingAccountRequestDTO request) throws CustomerNotFoundException {
        return bankAccountService.saveSavingBankAccount(request.getInitialBalance(), request.getInterestRate(), request.getCustomerId());
    }

    @GetMapping("/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/{accountId}/history")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="size", defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId, page, size);
    }

    @PostMapping("/debit")
    public void debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.debit(debitDTO);
    }

    @PostMapping("/credit")
    public void credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        this.bankAccountService.credit(creditDTO);
    }

    @PostMapping("/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.transfer(transferRequestDTO);
    }
}