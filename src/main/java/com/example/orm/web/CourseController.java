package com.example.orm.web;

import com.example.orm.service.CourseService;
import com.example.orm.web.dto.CourseResponse;
import com.example.orm.web.dto.CreateCourseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public CourseResponse create(@RequestBody @Valid CreateCourseRequest req) {
        return courseService.create(req);
    }

    @GetMapping("/{id}")
    public CourseResponse get(@PathVariable Long id) {
        return courseService.get(id);
    }

    @GetMapping
    public List<CourseResponse> list() {
        return courseService.list();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }
}
