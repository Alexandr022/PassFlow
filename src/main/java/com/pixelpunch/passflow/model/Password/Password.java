package com.pixelpunch.passflow.model.Password;

import com.pixelpunch.passflow.model.User.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "passwords")
@NoArgsConstructor
@AllArgsConstructor
public class Password {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int length;

    private boolean includeUppercases;

    private boolean includeNumbers;

    private boolean includeSpecials;

    private String passwords;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "password_id")
    private Password password;

    @ManyToMany(mappedBy = "passwords")
    private List<User> users = new ArrayList<>();

    public Password(String passwords, int length, boolean includeUppercases, boolean includeNumbers, boolean includeSpecials) {
        this.passwords = passwords;
        this.length = length;
        this.includeUppercases = includeUppercases;
        this.includeNumbers = includeNumbers;
        this.includeSpecials = includeSpecials;
    }
}
