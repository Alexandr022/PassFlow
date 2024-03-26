package com.pixelpunch.passflow.repository.PasswordRepository;

import com.pixelpunch.passflow.model.Password.HistoryPasswords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryPasswordsRepository extends JpaRepository<HistoryPasswords, Long> {
    List<HistoryPasswords> findByPasswordId(Long passwordId);
}
