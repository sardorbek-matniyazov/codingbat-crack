package com.example.codingbat.payload;

import com.example.codingbat.entity.MainSection;
import com.example.codingbat.entity.ProblemGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProblemGroupDto {
    @NotNull(message = "Problem group name is required")
    private String name;

    @NotNull(message = "Problem group description is required")
    private String description;

    private int reaction;

    @NotNull(message = "Problem group main section is required")
    private Long sectionId;

    public ProblemGroup toEntity(MainSection byId) {
        return new ProblemGroup(
                name,
                description,
                reaction,
                byId
        );
    }

    public ProblemGroup toEntity(Long id, MainSection byId) {
        return new ProblemGroup(
                id,
                name,
                description,
                reaction,
                byId
        );
    }
}
