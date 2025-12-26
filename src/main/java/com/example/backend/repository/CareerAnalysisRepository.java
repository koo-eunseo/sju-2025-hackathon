package com.example.backend.repository;

import com.example.backend.entity.CareerAnalysisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CareerAnalysisRepository
        extends JpaRepository<CareerAnalysisEntity, Long> {

    @Modifying
    @Query("delete from CareerAnalysisEntity c where c.title = :title")
    void deleteByTitle(@Param("title") String title);

    List<CareerAnalysisEntity> findByTitle(String title);
}
