package com.elebe.secretsmanager.api.service;

import com.elebe.secretsmanager.api.request.SaveSecretRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

@Slf4j
@Service
public class SecretsManagerServiceImpl implements SecretsManagerService {
    private final SecretsManagerClient secretsManagerClient;

    public SecretsManagerServiceImpl(SecretsManagerClient secretsManagerClient) {
        this.secretsManagerClient = secretsManagerClient;
    }

    @Override
    public void saveSecret(SaveSecretRequest request) {
        log.debug("Saving secret with key '{}'.", request.getSecretName());

        try {
            secretsManagerClient.createSecret(builder -> builder
                    .name(request.getSecretName())
                    .secretString(request.getSecretValue())
                    .description("Secret created by Spring Secrets Manager Test Application.")
            );
            log.debug("Secret with key '{}' saved successfully.", request.getSecretName());
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving secret with key '%s'."
                    .formatted(request.getSecretName()), e);
        }
    }

    @Override
    public String getSecret(String secretName) {
        log.debug("Getting secret with key '{}'.", secretName);
        try {
            return secretsManagerClient.getSecretValue(builder -> builder.secretId(secretName)).secretString();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while getting secret with key '%s'.".formatted(secretName), e);
        }
    }
}
