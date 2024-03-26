package com.pixelpunch.passflow.service.interfaces.PasswordInterfaces;

import com.pixelpunch.passflow.model.Password.Password;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface PasswordService {
    String generatePassword(int length, boolean includeUppercase, boolean includeNumbers, boolean includeSpecial);

    String generateAndSavePassword(int length, boolean includeUppercase, boolean includeNumbers, boolean includeSpecial);

    Password savePasswordForUser(Long userId, int length, boolean includeUppercase, boolean includeNumbers, boolean includeSpecial);

    List<Password> getAllPasswords();

    void deletePassword(Long passwordId) throws ChangeSetPersister.NotFoundException;

    Password updatePassword(Password updatedPassword) throws ChangeSetPersister.NotFoundException;

    Password regeneratePassword(Long passwordId) throws ChangeSetPersister.NotFoundException;

    void deleteAllPasswords();
}
