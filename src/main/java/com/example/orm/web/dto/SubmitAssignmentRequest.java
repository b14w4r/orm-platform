package com.example.orm.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubmitAssignmentRequest(
        @NotNull Long studentId,
        @NotBlank String content
) {}
