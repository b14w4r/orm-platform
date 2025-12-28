package com.example.orm.web.dto;

import java.time.OffsetDateTime;

public record QuizResultResponse(
        Long quizSubmissionId,
        Integer score,
        Integer totalQuestions,
        OffsetDateTime takenAt
) {}
