package org.seasonthon.fakecheck.dto;

import lombok.Builder;

@Builder
public record Last7DaysAnalysisResponse(
        Long last7DaysScansCount,
        Long last7DaysDangerCount,
        Long last7DaysCautionCount
) {

}