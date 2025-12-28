package com.example.orm.web.dto;

import com.example.orm.enums.EnrollmentStatus;

import java.time.OffsetDateTime;

public record EnrollmentResponse(
        Long id,
        Long courseId,
        Long studentId,
        OffsetDateTime enrollDate,
        EnrollmentStatus status
) {}
