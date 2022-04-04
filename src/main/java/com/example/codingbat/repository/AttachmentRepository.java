package com.example.codingbat.repository;

import com.example.codingbat.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    void deleteAllByIntroId(Long intro_id);
    List<Attachment> findAllByIntroId(Long intro_id);

    Attachment findByIntroId(long parseLong);
}
