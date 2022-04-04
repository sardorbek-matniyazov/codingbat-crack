package com.example.codingbat.repository;

import com.example.codingbat.entity.AttachmentContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Long> {
    void deleteAllByAttachmentId(Long id);

    AttachmentContent findByAttachmentId(Long id);
}
