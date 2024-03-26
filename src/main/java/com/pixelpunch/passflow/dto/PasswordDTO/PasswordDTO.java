package com.pixelpunch.passflow.dto.PasswordDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pixelpunch.passflow.model.Password.HistoryPasswords;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class PasswordDTO {
    @JsonIgnore
    private Long id;

    private String password;

    private int length;

    private boolean includeUppercase;

    private boolean includeNumbers;

    private boolean includeSpecial;

    public PasswordDTO(String password) {
        this.password = password;
    }

    public PasswordDTO(String password, int length, boolean includeUppercase, boolean includeNumbers, boolean includeSpecial) {
        this.password = password;
        this.length = length;
        this.includeUppercase = includeUppercase;
        this.includeNumbers =includeNumbers;
        this.includeSpecial = includeSpecial;
    }
    
}
