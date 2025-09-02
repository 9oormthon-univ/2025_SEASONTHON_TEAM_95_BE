package org.seasonthon.fakecheck.controller;

import lombok.RequiredArgsConstructor;
import org.seasonthon.fakecheck.dto.AnalysisResponse;
import org.seasonthon.fakecheck.service.AnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/analysis")
@RequiredArgsConstructor
@RestController
public class AnalysisController {

    private final AnalysisService analysisService;

    @PostMapping()
    public ResponseEntity<AnalysisResponse> analysis(@RequestPart MultipartFile image) {
        AnalysisResponse response = analysisService.analyze(image);

        return ResponseEntity.ok(response);
    }

}