package com.elebe.secretsmanager.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ApiResponse <T> {

    @Builder.Default
    @JsonProperty("success")
    private boolean success = true;

    @JsonProperty("payload")
    private T payload;

    @Builder.Default
    @JsonProperty("timestamp")
    private LocalDateTime timestamp = LocalDateTime.now();

    public static <T> ApiResponse<T> success(T payload) {
        return ApiResponse.<T>builder()
                .payload(payload)
                .build();
    }

    public static <T> ApiResponse<T> failure(T payload) {
        return ApiResponse.<T>builder()
                .success(false)
                .payload(payload)
                .build();
    }
}
