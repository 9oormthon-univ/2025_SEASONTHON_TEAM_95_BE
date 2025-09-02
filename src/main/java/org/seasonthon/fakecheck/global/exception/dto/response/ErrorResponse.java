package org.seasonthon.fakecheck.global.exception.dto.response;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;

public record ErrorResponse(int statusCode, String message, Map<String, String> validation) {

    @Builder
    public ErrorResponse(int statusCode, String message, Map<String, String> validation) {
        this.statusCode = statusCode;
        this.message = message;
        this.validation = getValidation(validation);
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }

    private Map<String, String> getValidation(Map<String, String> validation) {
        if (validation != null) return validation;
        return new HashMap<>();
    }

}