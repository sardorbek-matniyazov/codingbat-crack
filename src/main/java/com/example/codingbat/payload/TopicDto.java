package com.example.codingbat.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class TopicDto {
    @NotBlank(message = "Topic name is required")
    private String name;
    @NotNull(message = "Main section is required")
    private Long sectionId;
}
