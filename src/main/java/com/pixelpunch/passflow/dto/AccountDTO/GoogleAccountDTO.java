package com.pixelpunch.passflow.dto.AccountDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class GoogleAccountDTO {
    private Long id;

    private String email;

    private String password;

    private Long userId;


}
