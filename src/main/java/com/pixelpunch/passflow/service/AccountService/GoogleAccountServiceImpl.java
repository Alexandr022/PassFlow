package com.pixelpunch.passflow.service.AccountService;

import com.pixelpunch.passflow.model.Account.GoogleAccount;
import com.pixelpunch.passflow.model.User.User;
import com.pixelpunch.passflow.repository.AccountRepository.GoogleAccountRepository;
import com.pixelpunch.passflow.service.interfaces.AccountInterfaces.GoogleAccountService;
import com.pixelpunch.passflow.service.interfaces.UserInterfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoogleAccountServiceImpl implements GoogleAccountService {

    private final GoogleAccountRepository googleAccountRepository;
    private final UserService userService;

    @Autowired
    public GoogleAccountServiceImpl(GoogleAccountRepository googleAccountRepository, UserService userService) {
        this.googleAccountRepository = googleAccountRepository;
        this.userService = userService;
    }

    public List<GoogleAccount> getAllGoogleAccounts() {
        return googleAccountRepository.findAll();
    }

    public List<GoogleAccount> getGoogleAccountsByUserId(Long userId) {
        return googleAccountRepository.findByUserId(userId);
    }

    @Override
    public GoogleAccount createGoogleAccount(Long userId, GoogleAccount googleAccount) {
        User user = (User) userService.getUserById(userId);
        if (user == null) {
            return null;
        }

        googleAccount.setUser(user);

        return googleAccountRepository.save(googleAccount);
    }

    public GoogleAccount updateGoogleAccount(Long id, GoogleAccount googleAccount) {
        Optional<GoogleAccount> existingGoogleAccountOptional = googleAccountRepository.findById(id);
        if (existingGoogleAccountOptional.isPresent()) {
            googleAccount.setId(id);
            return googleAccountRepository.save(googleAccount);
        } else {
            throw new IllegalArgumentException("Google Account with id " + id + " not found.");
        }
    }

    public void deleteGoogleAccount(Long id) {
        Optional<GoogleAccount> existingGoogleAccountOptional = googleAccountRepository.findById(id);
        existingGoogleAccountOptional.ifPresent(googleAccountRepository::delete);
    }


}
