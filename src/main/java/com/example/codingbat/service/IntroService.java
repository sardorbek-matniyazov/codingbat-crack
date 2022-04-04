package com.example.codingbat.service;

import com.example.codingbat.entity.Intro;
import com.example.codingbat.payload.IntroDto;
import com.example.codingbat.payload.Status;
import com.example.codingbat.repository.IntroRepository;
import com.example.codingbat.repository.MainSectionRepository;
import com.example.codingbat.repository.ProblemRepository;
import com.example.codingbat.repository.TopicRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Service
public record IntroService (IntroRepository repository,
                            MainSectionRepository sectionRepository,
                            TopicRepository topicRepository,
                            ProblemRepository problemRepository,
                            AttachmentService attachmentService) {
    public List<Intro> getAll() {
        return repository.findAll();
    }

    public Intro getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Status add(IntroDto dto) {

        if (repository.existsByNameAndMainSection_Id(dto.getName(), dto.getSectionId()))
            return Status.builder()
                    .status(false)
                    .message("Intro with this name already exists in this section")
                    .build();

        if (!sectionRepository.existsById(dto.getSectionId()))
            return Status.builder()
                    .status(false)
                    .message("Section with this id doesn't exist")
                    .build();

        Intro intro = new Intro(
                dto.getName(),
                dto.getDescription(),
                dto.getKey(),
                sectionRepository.getById(dto.getSectionId())
        );

        if (dto.getTopicId() != null)
            if (topicRepository.existsById(dto.getTopicId())) {
                intro.setTopic(
                        topicRepository.getById(
                                dto.getTopicId()
                        )
                );
            } else return Status.builder()
                    .status(false)
                    .message("Topic with this id doesn't exist")
                    .build();

        if (dto.getProblemId() != null)
            if (problemRepository.existsById(dto.getProblemId())) {
                intro.setProblem(
                        problemRepository.getById(
                                dto.getProblemId()
                        )
                );
            } else return Status.builder()
                    .status(false)
                    .message("Problem with this id doesn't exist")
                    .build();

        if (dto.getVideoUrl() != null)
            intro.setVideoUrl(dto.getVideoUrl());

        repository.save(intro);

        return Status.builder()
                .status(true)
                .message("Intro added successfully")
                .build();
    }

    private void checkFile(MultipartHttpServletRequest request, Intro intro) {
        request.getFileNames().forEachRemaining(fileName -> {
            MultipartFile file = request.getFile(fileName);
            if (file != null)
                attachmentService.saveImageIntro(file, intro);
        });
    }

    public Status edit(Long id, IntroDto dto, MultipartHttpServletRequest request) {
        if (!repository.existsById(id))
            return Status.builder()
                    .status(false)
                    .message("Intro with this id doesn't exist")
                    .build();

        if (repository.existsByNameAndMainSection_IdAndIdNot(dto.getName(), dto.getSectionId(), id))
            return Status.builder()
                    .status(false)
                    .message("Intro with this name already exists in this section")
                    .build();

        Intro intro = new Intro(
                id,
                dto.getName(),
                dto.getDescription(),
                dto.getKey(),
                sectionRepository.getById(dto.getSectionId())
        );

        if (dto.getTopicId() != null)
            if (topicRepository.existsById(dto.getTopicId())) {
                intro.setTopic(
                        topicRepository.getById(
                                dto.getTopicId()
                        )
                );
            } else return Status.builder()
                    .status(false)
                    .message("Topic with this id doesn't exist")
                    .build();

        if (dto.getProblemId() != null)
            if (problemRepository.existsById(dto.getProblemId())) {
                intro.setProblem(
                        problemRepository.getById(
                                dto.getProblemId()
                        )
                );
            } else return Status.builder()
                    .status(false)
                    .message("Problem with this id doesn't exist")
                    .build();

        if (dto.getVideoUrl() != null)
            intro.setVideoUrl(dto.getVideoUrl());

        intro = repository.save(intro);
        checkFile(request, intro);

        return Status.builder()
                .status(true)
                .message("Intro edited successfully")
                .build();
    }

    public Status delete(Long id) {
        if (!repository.existsById(id))
            return Status.builder()
                    .status(false)
                    .message("Intro with this id doesn't exist")
                    .build();

        attachmentService.deleteLasts(id);
        repository.deleteById(id);
        return Status.builder()
                .status(true)
                .message("Intro deleted successfully")
                .build();
    }

    public Status upload(Long introId, MultipartHttpServletRequest request) {
        if (!repository.existsById(introId))
            return Status.builder()
                    .status(false)
                    .message("Intro with this id doesn't exist")
                    .build();

        checkFile(request, repository.getById(introId));
        return Status.builder()
                .status(true)
                .message("Intro uploaded successfully")
                .build();
    }
}
