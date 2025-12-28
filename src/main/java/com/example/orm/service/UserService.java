package com.example.orm.service;

import com.example.orm.entity.User;
import com.example.orm.repository.UserRepository;
import com.example.orm.web.dto.CreateUserRequest;
import com.example.orm.web.dto.UserResponse;
import com.example.orm.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse create(CreateUserRequest req) {
        User u = User.builder()
                .name(req.name())
                .email(req.email())
                .role(req.role())
                .build();
        User saved = userRepository.save(u);
        return new UserResponse(saved.getId(), saved.getName(), saved.getEmail(), saved.getRole());
    }

    public UserResponse get(Long id) {
        User u = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found: " + id));
        return new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole());
    }

    public List<UserResponse> list() {
        return userRepository.findAll().stream()
                .map(u -> new UserResponse(u.getId(), u.getName(), u.getEmail(), u.getRole()))
                .toList();
    }

    public void delete(Long id) {
        userRepository.delete(userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found: " + id)));
    }
}
