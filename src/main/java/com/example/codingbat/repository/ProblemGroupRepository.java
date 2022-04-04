package com.example.codingbat.repository;

import com.example.codingbat.entity.ProblemGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemGroupRepository extends JpaRepository<ProblemGroup, Long> {
    boolean existsByName(String name);
    boolean existsByNameAndMainSection_Id(String name, Long id);
    boolean existsByNameAndMainSection_IdAndIdNot(String name, Long id, Long id2);
}
