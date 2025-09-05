package org.seasonthon.fakecheck.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.seasonthon.fakecheck.dto.AIDetectionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

@Transactional
@SpringBootTest
class AiDetectionServiceTest {

    @Autowired
    RestClient restClient;

    @Autowired
    AiDetectionService aiDetectionService;

    @DisplayName("ㅇ")
    @Test
    void aVoid() {
        aiDetectionService.detect(new AIDetectionRequest())
    }
}