package com.vogdo.digitalbankingapi.repositories;

import com.vogdo.digitalbankingapi.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    Page<AccountOperation> findByBankAccountId(String accountId, Pageable pageable);
}