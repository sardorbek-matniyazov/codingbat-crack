package com.example.codingbat.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class IntroDto {
    @NotBlank(message = "Please provide a name")
    private String name;
    @NotBlank(message = "Please provide a description")
    private String description;
    private String videoUrl;
    @NotBlank(message = "Please provide a keyword")
    private String key;
    private Long topicId;
    private Long problemId;
    @NotNull(message = "section is required")
    private Long sectionId;
}
