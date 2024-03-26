package com.pixelpunch.passflow.mapper;

import com.pixelpunch.passflow.dto.AccountDTO.GoogleAccountDTO;
import com.pixelpunch.passflow.model.Account.GoogleAccount;
import org.springframework.stereotype.Component;

@Component
public class GoogleMapper {

    public GoogleAccountDTO toDTO(GoogleAccount googleAccount) {
        GoogleAccountDTO googleAccountDTO = new GoogleAccountDTO();
        googleAccountDTO.setId(googleAccount.getId());
        googleAccountDTO.setEmail(googleAccount.getEmail());
        googleAccountDTO.setPassword(googleAccount.getPassword());
        return googleAccountDTO;
    }

    public GoogleAccountDTO toDTO(GoogleAccount googleAccount, Long userId) {
        GoogleAccountDTO googleAccountDTO = new GoogleAccountDTO();
        googleAccountDTO.setId(googleAccount.getId());
        googleAccountDTO.setEmail(googleAccount.getEmail());
        googleAccountDTO.setPassword(googleAccount.getPassword());
        googleAccountDTO.setUserId(userId);
        return googleAccountDTO;
    }

    public GoogleAccount toEntity(GoogleAccountDTO googleAccountDTO) {
        GoogleAccount googleAccount = new GoogleAccount();
        googleAccount.setId(googleAccountDTO.getId());
        googleAccount.setEmail(googleAccountDTO.getEmail());
        googleAccount.setPassword(googleAccountDTO.getPassword());
        return googleAccount;
    }
}
