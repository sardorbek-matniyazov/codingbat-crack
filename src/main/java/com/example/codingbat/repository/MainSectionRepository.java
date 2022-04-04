package com.example.codingbat.repository;

import com.example.codingbat.entity.MainSection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainSectionRepository extends JpaRepository<MainSection, Long> {
    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);
}
