package com.example.orm.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

public record TakeQuizRequest(
        @NotNull Long studentId,
        @NotEmpty Map<Long, Long> answers
) {}
