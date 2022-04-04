package com.example.codingbat.service;

import com.example.codingbat.entity.MainSection;
import com.example.codingbat.payload.Status;
import com.example.codingbat.repository.MainSectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record MainSectionService(MainSectionRepository repository) {

    public List<MainSection> getAll() {
        return repository.findAll();
    }

    public MainSection getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Status add(MainSection mainSection) {
        if (repository.existsByName(mainSection.getName()))
            return Status.builder()
                    .status(false)
                    .message("Main section with name " + mainSection.getName() + " already exists")
                    .build();
            repository.save(
                    MainSection.builder()
                            .description(mainSection.getDescription())
                            .name(mainSection.getName())
                            .build()
            );
            return Status.builder()
                    .message("Main section added successfully")
                    .status(true)
                    .build();
    }

    public Status edit(Long id, MainSection mainSection) {
        if (!repository.existsById(id))
            return Status.builder()
                    .message("Main section with id " + id + " does not exist")
                    .status(false)
                    .build();

        if (repository.existsByNameAndIdNot(mainSection.getName(), id))
            return Status.builder()
                    .status(false)
                    .message("Main section with name " + mainSection.getName() + " already exists")
                    .build();

        repository.save(
                MainSection.builder()
                        .id(id)
                        .description(mainSection.getDescription())
                        .name(mainSection.getName())
                        .build()
        );

        return Status.builder()
                .message("Main section edited successfully")
                .status(true)
                .build();
    }

    public Status delete(Long id) {
        if (!repository.existsById(id))
            return Status.builder()
                    .message("Main section with id " + id + " does not exist")
                    .status(false)
                    .build();
        try {
            repository.deleteById(id);
        }catch (Exception e){
            return Status.builder()
                    .message("Main section with id " + id + " is used in other entities")
                    .status(false)
                    .build();
        }

        return Status.builder()
                .message("Main section deleted successfully")
                .status(true)
                .build();
    }
}
