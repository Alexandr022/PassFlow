package com.pixelpunch.passflow.model.Password;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HistoryPasswords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "password_id", referencedColumnName = "id")
    private Password password;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
