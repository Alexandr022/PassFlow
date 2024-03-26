package com.pixelpunch.passflow.repository.AccountRepository;

import com.pixelpunch.passflow.model.Account.GoogleAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoogleAccountRepository extends JpaRepository<GoogleAccount, Long> {
    List<GoogleAccount> findByUserId(Long userId);
}