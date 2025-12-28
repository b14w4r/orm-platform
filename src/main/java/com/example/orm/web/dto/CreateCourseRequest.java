package com.example.orm.web.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateCourseRequest(
        @NotBlank String title,
        String description,
        Long categoryId,
        Long teacherId,
        String duration,
        LocalDate startDate
) {}
