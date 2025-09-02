package org.seasonthon.fakecheck.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.seasonthon.fakecheck.domain.Analysis;
import org.seasonthon.fakecheck.dto.AnalysisDashboardResponse;
import org.seasonthon.fakecheck.dto.AnalysisResponse;
import org.seasonthon.fakecheck.enums.RiskLevel;
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

        Double fakeProbability = aiDetectionService.detect(s3Url);

        Analysis analysis = Analysis.create(s3Url, fakeProbability);
        Long analysisId = analysisRepository.save(analysis).getId();

        return AnalysisResponse.builder()
                .analysisId(analysisId)
                .riskLevel(analysis.getRiskLevel().getDescription())
                .fakeProbability(fakeProbability)
                .imageUrl(s3Url)
                .build();
    }

    @Transactional(readOnly = true)
    public AnalysisDashboardResponse getDashboardStats() {
        // 오늘 0시 0분 0초
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        // 오늘 23시 59분 59초 (또는 내일 0시 0분 0초 직전)
        LocalDateTime endOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        // Daily Scans (오늘 검사 수)
        Long dailyScansCount = analysisRepository.countByCreateTimeBetween(startOfDay, endOfDay);

        // Fake Detected (오늘 가짜 판별 수)
        Long fakeDetectedCount = analysisRepository.countByCreateTimeBetweenAndRiskLevel(
                startOfDay, endOfDay, RiskLevel.DANGER
        );

        return AnalysisDashboardResponse.builder()
                .dailyScansCount(dailyScansCount)
                .fakeDetectedCount(fakeDetectedCount)
                .build();
    }

}