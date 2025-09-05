package org.seasonthon.fakecheck.controller;

import lombok.RequiredArgsConstructor;
import org.seasonthon.fakecheck.dto.AnalysisDashboardResponse;
import org.seasonthon.fakecheck.dto.AnalysisResponse;
import org.seasonthon.fakecheck.dto.Last7DaysAnalysisResponse;
import org.seasonthon.fakecheck.service.AnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping
    public ResponseEntity<AnalysisResponse> analysis(@RequestPart MultipartFile image) {
        AnalysisResponse response = analysisService.analyze(image);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats/dashboards")
    public ResponseEntity<AnalysisDashboardResponse> getDashboard() {
        AnalysisDashboardResponse response = analysisService.getDashboardStats();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/stats/last7days")
    public ResponseEntity<Last7DaysAnalysisResponse> getLast7DaysAnalysis() {
        Last7DaysAnalysisResponse response = analysisService.getLastSevenDaysAnalysis();

        return ResponseEntity.ok(response);
    }

}