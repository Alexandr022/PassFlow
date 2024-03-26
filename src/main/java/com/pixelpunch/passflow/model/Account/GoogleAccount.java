package com.pixelpunch.passflow.model.Account;

import com.pixelpunch.passflow.dto.AccountDTO.GoogleAccountDTO;
import com.pixelpunch.passflow.model.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "google_accounts")
public class GoogleAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
