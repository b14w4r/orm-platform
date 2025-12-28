package com.example.orm.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateAssignmentRequest(
        @NotNull Long lessonId,
        Long moduleId,
        @NotBlank String title,
        String description,
        LocalDate dueDate,
        Integer maxScore
) {}
