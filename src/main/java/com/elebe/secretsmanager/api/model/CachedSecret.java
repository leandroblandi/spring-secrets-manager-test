package com.elebe.secretsmanager.api.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class CachedSecret {
    private String secretValue;
    private Instant cacheTime;

    public static CachedSecret create(String secretValue) {
        return CachedSecret.builder()
                .secretValue(secretValue)
                .cacheTime(Instant.now())
                .build();
    }

    public boolean isExpired() {
        return cacheTime.plusSeconds(60).isBefore(Instant.now());
    }
}
