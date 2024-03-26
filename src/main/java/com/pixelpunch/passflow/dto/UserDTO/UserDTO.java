package com.pixelpunch.passflow.dto.UserDTO;


import com.pixelpunch.passflow.dto.AccountDTO.GoogleAccountDTO;
import com.pixelpunch.passflow.model.Password.Password;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserDTO {

    private Long id;

    private String email;

    private String password;

    private List<GoogleAccountDTO> googleAccounts;

    private List<Password> passwords;

}
