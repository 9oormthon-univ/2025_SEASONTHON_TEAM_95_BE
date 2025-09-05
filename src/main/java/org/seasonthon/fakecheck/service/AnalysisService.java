package org.seasonthon.fakecheck.service;

import static org.seasonthon.fakecheck.enums.RiskLevel.CAUTION;
import static org.seasonthon.fakecheck.enums.RiskLevel.DANGER;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.seasonthon.fakecheck.domain.Analysis;
import org.seasonthon.fakecheck.dto.AnalysisDashboardResponse;
import org.seasonthon.fakecheck.dto.AnalysisResponse;
import org.seasonthon.fakecheck.repository.AnalysisRepository;
import org.seasonthon.fakecheck.s3.S3Provider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final AiDetectionService aiDetectionService;
    private final S3Provider s3Provider;

    public AnalysisResponse analyze(MultipartFile image) {
        String s3Url = s3Provider.uploadImage(image);

        AnalysisResponse response = aiDetectionService.detect(s3Url);

        Analysis analysis = Analysis.create(s3Url, response.aiProbability(), response.realProbability(), response.conclusion());

        Long analysisId = analysisRepository.save(analysis).getId();

        return response.withAnalysisId(analysisId);
    }

    @Transactional(readOnly = true)
    public AnalysisDashboardResponse getDashboardStats() {
        // 오늘 0시 0분 0초
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        // 오늘 23시 59분 59초 (또는 내일 0시 0분 0초 직전)
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        Long todayScansCount = analysisRepository.countByCreatedAtBetween(startOfDay, endOfDay);
        Long todayDangerCount = analysisRepository.countByCreatedAtBetweenAndRiskLevel(startOfDay, endOfDay, DANGER);
        Long todayCautionCount = analysisRepository.countByCreatedAtBetweenAndRiskLevel(startOfDay, endOfDay, CAUTION);
        Long totalDangerCount = analysisRepository.countByRiskLevel(DANGER);

        return AnalysisDashboardResponse.builder()
                .todayScansCount(todayScansCount)
                .todayDangerCount(todayDangerCount)
                .todayCautionCount(todayCautionCount)
                .totalDangerCount(totalDangerCount)
                .build();
    }

}