package com.elebe.secretsmanager.api.service;

import com.elebe.secretsmanager.api.request.SaveSecretRequest;

public interface SecretsManagerService {
    String getSecret(String secretName);
    void saveSecret(SaveSecretRequest request);
}
