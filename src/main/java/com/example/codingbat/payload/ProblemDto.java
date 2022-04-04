package com.example.codingbat.payload;

import com.example.codingbat.entity.ProblemTest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProblemDto {
    @NotBlank(message = "Title is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    @NotBlank(message = "Keyword is required")
    private String key;
    @NotNull(message = "Difficulty is required")
    private Long groupId;
    @NotNull(message = "Difficulty is required")
    private double difficulty;
    @NotNull(message = "Problem tests is required")
    private List<ProblemTest> tests;

}
