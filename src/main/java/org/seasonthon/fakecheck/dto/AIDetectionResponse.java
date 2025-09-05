package org.seasonthon.fakecheck.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;

public record AIDetectionResponse(
        String status,
        @JsonProperty("image_url") String imageUrl,
        Results results,
        @JsonProperty("final_decision") String finalDecision,
        String confidence,
        String conclusion,
        List<String> evidence
) {

    @Builder
    public record Results(
            @JsonProperty("cross_validation") CrossValidation crossValidation
    ) {

    }

    @Builder
    public record CrossValidation(
            @JsonProperty("ai_percentage") Double aiPercentage,
            @JsonProperty("real_percentage") Double realPercentage
    ) {

    }

}
