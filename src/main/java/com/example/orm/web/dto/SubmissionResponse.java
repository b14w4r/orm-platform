package com.example.orm.web.dto;

import java.time.OffsetDateTime;

public record SubmissionResponse(
        Long id,
        Long assignmentId,
        Long studentId,
        OffsetDateTime submittedAt,
        String content,
        Integer score,
        String feedback
) {}
