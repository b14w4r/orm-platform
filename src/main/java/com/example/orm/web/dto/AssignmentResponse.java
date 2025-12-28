package com.example.orm.web.dto;

import java.time.LocalDate;

public record AssignmentResponse(
        Long id,
        String title,
        String description,
        LocalDate dueDate,
        Integer maxScore,
        Long lessonId,
        Long moduleId
) {}
