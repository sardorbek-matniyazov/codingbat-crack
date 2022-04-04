package com.example.codingbat.service;

import com.example.codingbat.entity.Topic;
import com.example.codingbat.payload.Status;
import com.example.codingbat.payload.TopicDto;
import com.example.codingbat.repository.MainSectionRepository;
import com.example.codingbat.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record TopicService(TopicRepository repository,
                           MainSectionRepository mainSectionRepository) {
    public List<Topic> getAll() {
        return repository.findAll();
    }

    public Topic getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Status add(TopicDto dto) {
        if (!mainSectionRepository.existsById(dto.getSectionId()))
            return Status.builder()
                    .status(false)
                    .message("Main section not found")
                    .build();

        if (repository.existsByName(dto.getName()))
            return Status.builder()
                    .status(false)
                    .message("Topic with this name already exists")
                    .build();

        repository.save(
                Topic.builder()
                        .name(dto.getName())
                        .mainSection(mainSectionRepository.getById(dto.getSectionId()))
                        .build()
        );

        return Status.builder()
                .status(true)
                .message("Topic added")
                .build();
    }

    public Status edit(Long id, TopicDto dto) {
        if (repository.existsById(id))
            return Status.builder()
                    .status(false)
                    .message("Topic not found")
                    .build();

        if (!mainSectionRepository.existsById(dto.getSectionId()))
            return Status.builder()
                    .status(false)
                    .message("Main section not found")
                    .build();

        if (repository.existsByNameAndIdNot(dto.getName(), id))
            return Status.builder()
                    .status(false)
                    .message("Topic with this name already exists")
                    .build();

        repository.save(
                Topic.builder()
                        .id(id)
                        .name(dto.getName())
                        .mainSection(mainSectionRepository.getById(dto.getSectionId()))
                        .build()
        );

        return Status.builder()
                .status(true)
                .message("Topic edited")
                .build();
    }

    public Status delete(Long id) {
        if (!repository.existsById(id))
            return Status.builder()
                    .status(false)
                    .message("Topic not found")
                    .build();
        try {
            repository.deleteById(id);
        }catch (Exception e){
            return Status.builder()
                    .message("Topic with id " + id + " is used in other entities")
                    .status(false)
                    .build();
        }
        return Status.builder()
                .status(true)
                .message("Topic deleted")
                .build();
    }
}
