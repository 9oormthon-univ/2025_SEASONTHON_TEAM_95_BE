package org.seasonthon.fakecheck.service;

import lombok.RequiredArgsConstructor;
import org.seasonthon.fakecheck.dto.AnalysisResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * AI 생성 콘텐츠 탐지 서비스
 **/

@RequiredArgsConstructor
@Service
public class AiDetectionService {

    private final RestClient restClient;

    AnalysisResponse detect(String s3Url) {

        return AnalysisResponse.builder().build();
    }

}
