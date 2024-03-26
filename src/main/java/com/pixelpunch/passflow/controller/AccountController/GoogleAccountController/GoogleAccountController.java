package com.pixelpunch.passflow.controller.AccountController.GoogleAccountController;

import com.pixelpunch.passflow.dto.AccountDTO.GoogleAccountDTO;
import com.pixelpunch.passflow.mapper.GoogleMapper;
import com.pixelpunch.passflow.model.Account.GoogleAccount;
import com.pixelpunch.passflow.service.AccountService.GoogleAccountServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/google-accounts")
public class GoogleAccountController {

    private final GoogleAccountServiceImpl googleAccountService;
    private final GoogleMapper googleAccountMapper;

    public GoogleAccountController(GoogleAccountServiceImpl googleAccountService, GoogleMapper googleAccountMapper) {
        this.googleAccountService = googleAccountService;
        this.googleAccountMapper = googleAccountMapper;
    }

    @GetMapping("/get-google-accounts")
    public ResponseEntity<List<GoogleAccountDTO>> getGoogleAccounts() {
        List<GoogleAccount> googleAccounts = googleAccountService.getAllGoogleAccounts();
        List<GoogleAccountDTO> googleAccountDTOs = new ArrayList<>();
        for (GoogleAccount googleAccount : googleAccounts) {
            googleAccountDTOs.add(googleAccountMapper.toDTO(googleAccount));
        }
        return ResponseEntity.ok(googleAccountDTOs);
    }

    @PostMapping("/create-google-account")
    public ResponseEntity<GoogleAccountDTO> createGoogleAccount(@RequestParam Long userId,
                                                                @RequestBody GoogleAccountDTO googleAccountDTO) {
        GoogleAccount googleAccount = googleAccountMapper.toEntity(googleAccountDTO);
        GoogleAccount createdGoogleAccount = googleAccountService.createGoogleAccount(userId, googleAccount);
        GoogleAccountDTO createdGoogleAccountDTO = googleAccountMapper.toDTO(createdGoogleAccount, userId);
        return ResponseEntity.ok(createdGoogleAccountDTO);
    }
}
