package org.seasonthon.fakecheck.repository;

import java.time.LocalDateTime;
import org.seasonthon.fakecheck.domain.Analysis;
import org.seasonthon.fakecheck.enums.RiskLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
    // 오늘 전체 검사 수
    Long countByCreateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    // 오늘 특정 위험도별 카운트
    Long countByCreateTimeBetweenAndRiskLevel(
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            RiskLevel riskLevel
    );

    Long countByRiskLevel(RiskLevel riskLevel);

}