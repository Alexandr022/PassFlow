package com.pixelpunch.passflow.mapper;

import com.pixelpunch.passflow.dto.PasswordDTO.PasswordDTO;
import com.pixelpunch.passflow.model.Password.Password;
import org.springframework.stereotype.Component;

@Component
public class PasswordMapper {

    public PasswordDTO toDTO(Password password) {
        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setId(password.getId());
        passwordDTO.setPassword(password.getPasswords());
        passwordDTO.setLength(password.getLength());
        passwordDTO.setIncludeUppercase(password.isIncludeUppercases());
        passwordDTO.setIncludeNumbers(password.isIncludeNumbers());
        passwordDTO.setIncludeSpecial(password.isIncludeSpecials());
        return passwordDTO;
    }

    public Password toEntity(PasswordDTO passwordDTO) {
        Password password = new Password();
        password.setId(passwordDTO.getId());
        password.setPasswords(passwordDTO.getPassword());
        password.setLength(passwordDTO.getLength());
        password.setIncludeUppercases(passwordDTO.isIncludeUppercase());
        password.setIncludeNumbers(passwordDTO.isIncludeNumbers());
        password.setIncludeSpecials(passwordDTO.isIncludeSpecial());
        return password;
    }
}
