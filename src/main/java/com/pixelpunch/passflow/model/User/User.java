package com.pixelpunch.passflow.model.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixelpunch.passflow.model.Account.GoogleAccount;
import com.pixelpunch.passflow.model.Password.Password;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_password",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "password_id")
    )
    private List<Password> passwords;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<GoogleAccount> googleAccounts;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
