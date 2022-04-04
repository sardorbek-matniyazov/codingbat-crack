package com.example.codingbat.service;

import com.example.codingbat.entity.ProblemGroup;
import com.example.codingbat.payload.ProblemGroupDto;
import com.example.codingbat.payload.Status;
import com.example.codingbat.repository.MainSectionRepository;
import com.example.codingbat.repository.ProblemGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record ProblemGroupService (ProblemGroupRepository repository,
                                   MainSectionRepository mainSectionRepository) {
    public List<ProblemGroup> getAll() {
        return repository.findAll();
    }

    public ProblemGroup getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Status add(ProblemGroupDto dto) {
        if (repository.existsByNameAndMainSection_Id(dto.getName(), dto.getSectionId()))
            return Status.builder()
                    .status(false)
                    .message("Problem group with name " + dto.getName() + " already exists")
                    .build();
        if (!mainSectionRepository.existsById(dto.getSectionId()))
            return Status.builder()
                    .message("Main section with id " + dto.getSectionId() + " does not exist")
                    .status(false)
                    .build();

        repository.save(
                dto.toEntity(
                        mainSectionRepository.getById(
                                dto.getSectionId()
                        )
                )
        );

        return Status.builder()
                .message("successfully added problem group")
                .status(true)
                .build();
    }

    public Status edit(Long id, ProblemGroupDto dto) {
        if (!repository.existsById(id))
            return Status.builder()
                    .status(false)
                    .message("Problem group with id " + id + " does not exist")
                    .build();
        if (repository.existsByNameAndMainSection_IdAndIdNot(dto.getName(), dto.getSectionId(), id))
            return Status.builder()
                    .status(false)
                    .message("Problem group with name " + dto.getName() + " already exists")
                    .build();

        if (!mainSectionRepository.existsById(dto.getSectionId()))
            return Status.builder()
                    .message("Main section with id " + dto.getSectionId() + " does not exist")
                    .status(false)
                    .build();

        repository.save(
                dto.toEntity(
                        id,
                        mainSectionRepository.getById(
                                dto.getSectionId()
                        )
                )
        );

        return Status.builder()
                .message("successfully edited problem group")
                .status(true)
                .build();
    }

    public Status delete(Long id) {
        if (!repository.existsById(id))
            return Status.builder()
                    .status(false)
                    .message("Problem group with id " + id + " does not exist")
                    .build();
        try {
            repository.deleteById(id);
        }catch (Exception e){
            return Status.builder()
                    .message("Problem Group with id " + id + " is used in other entities")
                    .status(false)
                    .build();
        }

        return Status.builder()
                .message("successfully deleted problem group")
                .status(true)
                .build();
    }
}
