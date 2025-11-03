package com.elebe.secretsmanager.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SaveSecretRequest {

    @JsonProperty("secret_name")
    private String secretName;

    @JsonProperty("secret_value")
    private String secretValue;
}
