package com.example.orm.service;

import com.example.orm.entity.Category;
import com.example.orm.entity.Course;
import com.example.orm.entity.User;
import com.example.orm.repository.CategoryRepository;
import com.example.orm.repository.CourseRepository;
import com.example.orm.repository.UserRepository;
import com.example.orm.web.dto.CourseResponse;
import com.example.orm.web.dto.CreateCourseRequest;
import com.example.orm.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CourseResponse create(CreateCourseRequest req) {
        Course c = Course.builder()
                .title(req.title())
                .description(req.description())
                .duration(req.duration())
                .startDate(req.startDate())
                .build();

        if (req.categoryId() != null) {
            Category cat = categoryRepository.findById(req.categoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found: " + req.categoryId()));
            c.setCategory(cat);
        }
        if (req.teacherId() != null) {
            User teacher = userRepository.findById(req.teacherId())
                    .orElseThrow(() -> new NotFoundException("Teacher not found: " + req.teacherId()));
            c.setTeacher(teacher);
        }

        return toResponse(courseRepository.save(c));
    }

    public CourseResponse get(Long id) {
        Course c = courseRepository.findById(id).orElseThrow(() -> new NotFoundException("Course not found: " + id));
        return toResponse(c);
    }

    public List<CourseResponse> list() {
        return courseRepository.findAll().stream().map(this::toResponse).toList();
    }

    public void delete(Long id) {
        courseRepository.delete(courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found: " + id)));
    }

    private CourseResponse toResponse(Course c) {
        Long categoryId = c.getCategory() != null ? c.getCategory().getId() : null;
        Long teacherId = c.getTeacher() != null ? c.getTeacher().getId() : null;
        return new CourseResponse(c.getId(), c.getTitle(), c.getDescription(), c.getDuration(), c.getStartDate(), categoryId, teacherId);
    }
}
