package org.seasonthon.fakecheck.dto;

import lombok.Builder;

@Builder
public record AnalysisDashboardResponse(
        Long dailyScansCount,
        Long fakeDetectedCount
) {

}