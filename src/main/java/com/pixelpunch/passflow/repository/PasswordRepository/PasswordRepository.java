package com.pixelpunch.passflow.repository.PasswordRepository;

import com.pixelpunch.passflow.model.Password.Password;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
}
