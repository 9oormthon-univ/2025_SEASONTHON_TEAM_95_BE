package org.seasonthon.fakecheck.service;

import lombok.RequiredArgsConstructor;
import org.seasonthon.fakecheck.domain.Analysis;
import org.seasonthon.fakecheck.dto.AnalysisResponse;
import org.seasonthon.fakecheck.repository.AnalysisRepository;
import org.seasonthon.fakecheck.s3.S3Provider;
import org.springframework.stereotype.Service;
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

}