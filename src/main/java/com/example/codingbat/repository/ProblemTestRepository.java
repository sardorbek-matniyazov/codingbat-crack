package com.example.codingbat.repository;

import com.example.codingbat.entity.ProblemTest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemTestRepository extends JpaRepository<ProblemTest, Long> {
    void deleteAllByProblemId(Long id);
}
