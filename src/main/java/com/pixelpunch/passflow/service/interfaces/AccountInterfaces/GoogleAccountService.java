package com.pixelpunch.passflow.service.interfaces.AccountInterfaces;

import com.pixelpunch.passflow.model.Account.GoogleAccount;

public interface GoogleAccountService {
    GoogleAccount createGoogleAccount(Long userId, GoogleAccount googleAccount);
}
