package org.seasonthon.fakecheck.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.seasonthon.fakecheck.dto.AIDetectionRequest;
import org.seasonthon.fakecheck.dto.AIDetectionResponse;
import org.seasonthon.fakecheck.dto.AnalysisResponse;
import org.seasonthon.fakecheck.global.exception.InternalServerException;
import org.seasonthon.fakecheck.global.exception.dto.ErrorStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * AI 생성 콘텐츠 탐지 서비스
 **/

@Slf4j
@RequiredArgsConstructor
@Service
public class AiDetectionService {

    private final RestClient restClient;

    AnalysisResponse detect(AIDetectionRequest request) {

        AIDetectionResponse aiResponse = restClient.post()
                .uri("/analyze")  // AI 서버의 엔드포인트
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    log.error("AI 서버 클라이언트 오류: {}", res.getStatusCode());
                    throw new InternalServerException(ErrorStatus.INTERNAL_SERVER_ERROR);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    log.error("AI 서버 내부 오류: {}", res.getStatusCode());
                    throw new InternalServerException(ErrorStatus.INTERNAL_SERVER_ERROR);
                })
                .body(AIDetectionResponse.class);

        log.info("AI 서버 응답 수신: {}", aiResponse.finalDecision());

        return convertToAnalysisResponse(aiResponse);
    }

    private AnalysisResponse convertToAnalysisResponse(AIDetectionResponse aiResponse) {
        return AnalysisResponse.builder()
                .imageUrl(aiResponse.imageUrl())
                .aiProbability(aiResponse.results().crossValidation().aiPercentage())
                .realProbability(aiResponse.results().crossValidation().realPercentage())
                .conclusion(aiResponse.conclusion())
                .evidences(aiResponse.evidence())
                .build();
    }
}
