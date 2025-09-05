package org.seasonthon.fakecheck.dto;

import lombok.Builder;

@Builder
public record AnalysisDashboardResponse(
        Long todayScansCount,
        Long todayDangerCount,
        Long todayCautionCount,
        Long totalDangerCount
) {

}