package com.example.codingbat.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SolveDto {
    @NotBlank(message = "Please provide a problem statement")
    private String script;
    @NotNull(message = "Member is required")
    private Long memberId;
}
