package com.example.orm.web.dto;

import java.time.LocalDate;

public record CourseResponse(
        Long id,
        String title,
        String description,
        String duration,
        LocalDate startDate,
        Long categoryId,
        Long teacherId
) {}
