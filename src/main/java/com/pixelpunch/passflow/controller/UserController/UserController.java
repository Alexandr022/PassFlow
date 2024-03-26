package com.pixelpunch.passflow.controller.UserController;

import com.pixelpunch.passflow.dto.AccountDTO.GoogleAccountDTO;
import com.pixelpunch.passflow.dto.PasswordDTO.PasswordDTO;
import com.pixelpunch.passflow.dto.UserDTO.UserDTO;
import com.pixelpunch.passflow.mapper.GoogleMapper;
import com.pixelpunch.passflow.mapper.PasswordMapper;
import com.pixelpunch.passflow.model.Account.GoogleAccount;
import com.pixelpunch.passflow.model.Password.Password;
import com.pixelpunch.passflow.model.User.User;
import com.pixelpunch.passflow.service.interfaces.UserInterfaces.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    private final PasswordMapper passwordMapper;

    private final GoogleMapper googleAccountMapper;

    public UserController(UserService userService, PasswordMapper passwordMapper, GoogleMapper googleAccountMapper) {
        this.userService = userService;
        this.passwordMapper = passwordMapper;
        this.googleAccountMapper = googleAccountMapper;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email,
                                        @RequestParam String password) {
        log.info("GET endpoint /auth/login was called");
        User user = userService.findByEmailAndPassword(email, password);
        if (user != null) {
            return ResponseEntity.ok("Login successful.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
        }
    }

    @GetMapping("/get")
    public ResponseEntity<List<User>> getUsers() {
        log.info("GET endpoint /auth/get was called");
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserPasswords(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            List<GoogleAccount> googleAccounts = user.getGoogleAccounts();
            List<GoogleAccountDTO> googleAccountDTOs = new ArrayList<>();
            List<Password> passwords = user.getPasswords();
            List<PasswordDTO> passwordDTOS = new ArrayList<>();
            for (GoogleAccount googleAccount : googleAccounts) {
                googleAccountDTOs.add(new GoogleAccountDTO(googleAccount.getId(), googleAccount.getEmail(), googleAccount.getPassword(), googleAccount.getId()));
            }
            for (Password password : passwords) {
                passwordDTOS.add(new PasswordDTO(password.getId(), password.getPasswords(), password.getLength(), password.isIncludeUppercases(), password.isIncludeNumbers(), password.isIncludeSpecials()));
            }
            UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getPassword(), googleAccountDTOs, passwords);
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            List<GoogleAccountDTO> googleAccountDTOs = new ArrayList<>();
            List<PasswordDTO> passwordDTOs = new ArrayList<>();

            List<GoogleAccount> googleAccounts = user.getGoogleAccounts();
            for (GoogleAccount googleAccount : googleAccounts) {
                GoogleAccountDTO googleAccountDTO = googleAccountMapper.toDTO(googleAccount);
                googleAccountDTOs.add(googleAccountDTO);
            }

            List<Password> passwords = user.getPasswords();
            for (Password password : passwords) {
                PasswordDTO passwordDTO = passwordMapper.toDTO(password);
                passwordDTOs.add(passwordDTO);
            }

            UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getPassword(), googleAccountDTOs, passwords);
            userDTOs.add(userDTO);
        }

        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserWithPasswords(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> doRegister(@RequestBody UserDTO userDTO) {
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();

        User user = new User(email, password);

        User registeredUser = userService.register(user);
        if (registeredUser != null) {
            return ResponseEntity.ok("User registered successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this email already exists.");
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable Long userId,
                                             @RequestBody UserDTO userDTO) {
        User user = userService.getUserById(userId);
        if (user != null) {
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            userService.saveUser(user);
            return ResponseEntity.ok("User updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<String> deleteAllUsers() {
        try {
            userService.deleteAllUsers();
            return ResponseEntity.ok("All users deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting all users.");
        }
    }


}
