package com.example.codingbat.service;

import com.example.codingbat.entity.Attachment;
import com.example.codingbat.entity.AttachmentContent;
import com.example.codingbat.entity.Intro;
import com.example.codingbat.repository.AttachmentContentRepository;
import com.example.codingbat.repository.AttachmentRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Service
public record AttachmentService (AttachmentRepository repository,
                                 AttachmentContentRepository contentRepository) {

    @SneakyThrows
    public void saveImageIntro(MultipartFile file, Intro intro) {
        deleteLasts(intro.getId());
        String originalFilename = file.getOriginalFilename();
        byte[] bytes = file.getBytes();
        String name = file.getName();
        String contentType = file.getContentType();
        long size = file.getSize();
        Attachment attachment = new Attachment(
                originalFilename,
                name,
                contentType,
                size,
                intro
        );
        attachment = repository.save(attachment);
        AttachmentContent content = new AttachmentContent();
        content.setContent(bytes);
        content.setAttachment(attachment);
        contentRepository.save(content);
    }

    public void deleteLasts(Long id) {
        repository.findAllByIntroId(id).forEach(attachment -> {
            contentRepository.deleteAllByAttachmentId(attachment.getId());
        }
        );
    }
    @SneakyThrows
    public void getAttachment(String introId, HttpServletResponse response) {
        Attachment attachment = repository.findByIntroId(Long.parseLong(introId));
        if (attachment != null) {
            AttachmentContent content = contentRepository.findByAttachmentId(attachment.getId());
            response.setContentType(attachment.getContentType());
            response.setContentLength(content.getContent().length);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getOriginalFileName() + "\"");
            response.getOutputStream().write(content.getContent());
        }
    }
}
