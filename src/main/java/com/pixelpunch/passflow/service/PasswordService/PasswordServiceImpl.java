package com.pixelpunch.passflow.service.PasswordService;

import com.pixelpunch.passflow.model.Password.Password;
import com.pixelpunch.passflow.model.User.User;
import com.pixelpunch.passflow.repository.PasswordRepository.PasswordRepository;
import com.pixelpunch.passflow.service.interfaces.PasswordInterfaces.PasswordService;
import com.pixelpunch.passflow.service.interfaces.UserInterfaces.UserService;
import jakarta.transaction.Transactional;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final PasswordRepository passwordRepository;

    private final UserService userService;
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMERIC_CHARACTERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+[]{}|;:'\",.<>/?";

    public PasswordServiceImpl(PasswordRepository passwordRepository, UserService userService) {
        this.passwordRepository = passwordRepository;
        this.userService = userService;
    }

    @Override
    public String generatePassword(int length, boolean includeUppercase, boolean includeNumbers, boolean includeSpecial) {
        if (length < 1) {
            throw new IllegalArgumentException("Password length must be greater than zero");
        }

        StringBuilder password = new StringBuilder();
        String allCharacters = LOWERCASE_CHARACTERS + (includeUppercase ? UPPERCASE_CHARACTERS : "")
                + (includeNumbers ? NUMERIC_CHARACTERS : "") + (includeSpecial ? SPECIAL_CHARACTERS : "");

        for (int i = 0; i < length; i++) {
            int randomIndex = ThreadLocalRandom.current().nextInt(allCharacters.length());
            char randomChar = allCharacters.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }

    @Override
    public String generateAndSavePassword(int length, boolean includeUppercase, boolean includeNumbers, boolean includeSpecial) {
        String password = generatePassword(length, includeUppercase, includeNumbers, includeSpecial);
        Password entity = new Password(password, length, includeUppercase, includeNumbers, includeSpecial);
        passwordRepository.save(entity);
        return password;
    }

    @Override
    public Password savePasswordForUser(Long userId, int length, boolean includeUppercase, boolean includeNumbers, boolean includeSpecial) {
        String generatedPassword = generatePassword(length, includeUppercase, includeNumbers, includeSpecial);

        Password passwordEntity = new Password(generatedPassword, length, includeUppercase, includeNumbers, includeSpecial);

        passwordEntity = passwordRepository.save(passwordEntity);

        User user = userService.getUserById(userId);

        passwordEntity.getUsers().add(user);
        user.getPasswords().add(passwordEntity);
        userService.saveUser(user);

        return passwordEntity;
    }

    @Override
    public List<Password> getAllPasswords() {
        return passwordRepository.findAll();
    }

    @Override
    public void deletePassword(Long passwordId) throws ChangeSetPersister.NotFoundException {
        Optional<Password> optionalPassword = passwordRepository.findById(passwordId);
        if (optionalPassword.isPresent()) {
            Password password = optionalPassword.get();

            List<User> users = password.getUsers();
            for (User user : users) {
                user.getPasswords().remove(password);
                userService.saveUser(user);
            }
            passwordRepository.deleteById(passwordId);
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    @Override
    public Password updatePassword(Password updatedPassword) throws ChangeSetPersister.NotFoundException {
        Optional<Password> optionalPassword = passwordRepository.findById(updatedPassword.getId());
        if (optionalPassword.isPresent()) {
            Password existingPassword = optionalPassword.get();
            existingPassword.setPasswords(updatedPassword.getPasswords());
            existingPassword.setLength(updatedPassword.getLength());
            existingPassword.setIncludeUppercases(updatedPassword.isIncludeUppercases());
            existingPassword.setIncludeNumbers(updatedPassword.isIncludeNumbers());
            existingPassword.setIncludeSpecials(updatedPassword.isIncludeSpecials());
            return passwordRepository.save(existingPassword);
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    @Override
    public Password regeneratePassword(Long passwordId) throws ChangeSetPersister.NotFoundException {
        Optional<Password> optionalPassword = passwordRepository.findById(passwordId);
        if (optionalPassword.isPresent()) {
            Password password = optionalPassword.get();
            String regeneratedPassword = generatePassword(password.getLength(), password.isIncludeUppercases(), password.isIncludeNumbers(), password.isIncludeSpecials());
            password.setPasswords(regeneratedPassword);
            passwordRepository.save(password);
            return password;
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    @Override
    @Transactional
    public void deleteAllPasswords() {
        passwordRepository.deleteAll();
    }

}
