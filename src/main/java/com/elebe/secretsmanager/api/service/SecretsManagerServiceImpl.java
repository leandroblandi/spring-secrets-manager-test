package com.elebe.secretsmanager.api.service;

import com.elebe.secretsmanager.api.model.CachedSecret;
import com.elebe.secretsmanager.api.request.SaveSecretRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SecretsManagerServiceImpl implements SecretsManagerService {
    private final SecretsManagerClient secretsManagerClient;
    private final Map<String, CachedSecret> cachedSecrets;

    public SecretsManagerServiceImpl(SecretsManagerClient secretsManagerClient) {
        this.secretsManagerClient = secretsManagerClient;
        this.cachedSecrets = new ConcurrentHashMap<>();
    }

    @Override
    public void saveSecret(SaveSecretRequest request) {
        log.debug("Saving secret with key '{}'.", request.getSecretName());

        secretsManagerClient.createSecret(builder -> builder
                .name(request.getSecretName())
                .secretString(request.getSecretValue())
                .description("Secret created by Spring Secrets Manager Test Application.")
        );

        log.debug("Secret with key '{}' saved successfully.", request.getSecretName());
        saveSecretInCache(request.getSecretName(), request.getSecretValue());
    }

    @Override
    public String getSecret(String secretName) {
        log.debug("Getting secret with key '{}'.", secretName);

        if (cachedSecrets.containsKey(secretName) && !cachedSecrets.get(secretName).isExpired()) {
            log.debug("Secret with key '{}' is in cache, returning it...", secretName);
            return cachedSecrets.get(secretName).getSecretValue();
        }

        log.debug("Cache with key '{}' is not in cache, fetching it from AWS Secrets Manager...", secretName);
        String secretValue = secretsManagerClient.getSecretValue(builder -> builder.secretId(secretName)).secretString();

        saveSecretInCache(secretName, secretValue);
        return secretValue;
    }

    private void saveSecretInCache(String secretName, String secretValue) {
        cachedSecrets.putIfAbsent(secretName, CachedSecret.create(secretValue));
    }
}
