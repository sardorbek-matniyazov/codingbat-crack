package com.example.codingbat.repository;

import com.example.codingbat.entity.Intro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IntroRepository extends JpaRepository<Intro, Long> {
    boolean existsByNameAndMainSection_Id(String name, Long sectionId);

    boolean existsByNameAndMainSection_IdAndIdNot(String name, Long sectionId, Long id);
}
