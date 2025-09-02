package org.seasonthon.fakecheck.repository;

import org.seasonthon.fakecheck.domain.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {

}