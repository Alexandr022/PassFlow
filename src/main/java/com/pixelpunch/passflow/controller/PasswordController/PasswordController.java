package com.pixelpunch.passflow.controller.PasswordController;

import com.pixelpunch.passflow.component.ResponseCache;
import com.pixelpunch.passflow.dto.PasswordDTO.PasswordDTO;
import com.pixelpunch.passflow.mapper.PasswordMapper;
import com.pixelpunch.passflow.model.Password.HistoryPasswords;
import com.pixelpunch.passflow.model.Password.Password;
import com.pixelpunch.passflow.service.PasswordService.HistoryPasswordsServiceImpl;
import com.pixelpunch.passflow.service.PasswordService.PasswordServiceImpl;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    private final PasswordServiceImpl passwordGenerator;

    private final PasswordMapper passwordMapper;

    //private final ResponseCache responseCache;


    public PasswordController(PasswordServiceImpl passwordGenerator, PasswordMapper passwordMapper, ResponseCache responseCache) {
        this.passwordGenerator = passwordGenerator;
        this.passwordMapper = passwordMapper;
       // this.responseCache = responseCache;
    }

    @GetMapping("/generate")
    public PasswordDTO generatePassword(@RequestParam(name = "length", defaultValue = "12") int length,
                                        @RequestParam(name = "includeUppercase", defaultValue = "true") boolean includeUppercase,
                                        @RequestParam(name = "includeNumbers", defaultValue = "true") boolean includeNumbers,
                                        @RequestParam(name = "includeSpecial", defaultValue = "true") boolean includeSpecial) {

        String generatedPassword = passwordGenerator.generatePassword(length, includeUppercase, includeNumbers, includeSpecial);
        return new PasswordDTO(generatedPassword);
    }


    @GetMapping("/all")
    public ResponseEntity<List<PasswordDTO>> getAllPasswords() {
        List<Password> passwords = passwordGenerator.getAllPasswords();
        List<PasswordDTO> passwordDTOs = new ArrayList<>();
        for (Password password : passwords) {
            passwordDTOs.add(passwordMapper.toDTO(password));
        }
        return ResponseEntity.ok(passwordDTOs);
    }

    @PostMapping("/generate")
    public PasswordDTO generateAndSavePassword(@RequestParam(name = "length", defaultValue = "12") int length,
                                               @RequestParam(name = "includeUppercase", defaultValue = "true") boolean includeUppercase,
                                               @RequestParam(name = "includeNumbers", defaultValue = "true") boolean includeNumbers,
                                               @RequestParam(name = "includeSpecial", defaultValue = "true") boolean includeSpecial) {

        String generatedPassword = passwordGenerator.generateAndSavePassword(length, includeUppercase, includeNumbers, includeSpecial);
        return new PasswordDTO(generatedPassword, length, includeUppercase, includeNumbers, includeSpecial);
    }

    @PostMapping("/generate-for-user")
    public PasswordDTO generateAndSavePassword(@RequestParam Long userId,
                                               @RequestParam(name = "length", defaultValue = "12") int length,
                                               @RequestParam(name = "includeUppercase", defaultValue = "true") boolean includeUppercase,
                                               @RequestParam(name = "includeNumbers", defaultValue = "true") boolean includeNumbers,
                                               @RequestParam(name = "includeSpecial", defaultValue = "true") boolean includeSpecial) {
        Password savedPassword = passwordGenerator.savePasswordForUser(userId, length, includeUppercase, includeNumbers, includeSpecial);
        return passwordMapper.toDTO(savedPassword);
    }

    @PutMapping("/{passwordId}")
    public ResponseEntity<PasswordDTO> updatePassword(@PathVariable Long passwordId,
                                                      @RequestBody PasswordDTO updatedPasswordDTO) {
        try {
            Password updatedPassword = passwordMapper.toEntity(updatedPasswordDTO);
            updatedPassword.setId(passwordId);
            Password updatedPasswordEntity = passwordGenerator.updatePassword(updatedPassword);
            PasswordDTO updatedPasswordDTOResponse = passwordMapper.toDTO(updatedPasswordEntity);
            return ResponseEntity.ok(updatedPasswordDTOResponse);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{passwordId}/regenerate")
    public ResponseEntity<PasswordDTO> regeneratePassword(@PathVariable Long passwordId) {
        try {
            Password regeneratedPassword = passwordGenerator.regeneratePassword(passwordId);
            PasswordDTO regeneratedPasswordDTO = passwordMapper.toDTO(regeneratedPassword);
            return ResponseEntity.ok(regeneratedPasswordDTO);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{passwordId}")
    public void deletePassword(@PathVariable Long passwordId) throws ChangeSetPersister.NotFoundException {
        passwordGenerator.deletePassword(passwordId);
    }

    @DeleteMapping("/delete-all")
    public void deleteAllPasswords() {
        passwordGenerator.deleteAllPasswords();
    }

}
