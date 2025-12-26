package com.example.backend.repository;

import com.example.backend.entity.CompetencyResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetencyResultRepository extends JpaRepository<CompetencyResultEntity, Long> {
    List<CompetencyResultEntity> findByUserIdAndTitle(String userId, String title);
}