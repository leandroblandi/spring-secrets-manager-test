package com.elebe.secretsmanager.api.controller;

import com.elebe.secretsmanager.api.request.SaveSecretRequest;
import com.elebe.secretsmanager.api.response.ApiResponse;
import com.elebe.secretsmanager.api.service.SecretsManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/secrets")
public class SecretsController {
    private final SecretsManagerService secretsManagerService;

    public SecretsController(SecretsManagerService secretsManagerService) {
        this.secretsManagerService = secretsManagerService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<String>> getSecret(@RequestParam("secret_name") String secretName) {
        ApiResponse<String> response = ApiResponse.success(secretsManagerService.getSecret(secretName));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> saveSecret(@RequestBody SaveSecretRequest request) {
        secretsManagerService.saveSecret(request);
        ApiResponse<String> response = ApiResponse.success("Secret saved successfully.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
