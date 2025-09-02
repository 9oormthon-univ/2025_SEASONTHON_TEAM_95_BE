package org.seasonthon.fakecheck.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RiskLevel {

    SAFE("안전", 70.0),
    CAUTION("주의", 40.0),
    DANGER("위험", 0.0);

    private final String description;
    private final Double value;

    public static RiskLevel fromProbability(Double probability) {
        if (probability == null) return DANGER;               // null 이면 위험으로 처리

        if (probability >= SAFE.value) return SAFE;           // 70점 이상 → 안전
        else if (probability >= CAUTION.value) return CAUTION;// 40점 이상 → 주의
        else return DANGER;                             // 40점 미만 → 위험
    }

}