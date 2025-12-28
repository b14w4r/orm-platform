package com.example.orm.web.dto;

import com.example.orm.enums.UserRole;

public record UserResponse(Long id, String name, String email, UserRole role) {
}
