package com.vogdo.digitalbankingapi.repositories;

import com.vogdo.digitalbankingapi.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}