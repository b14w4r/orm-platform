package com.example.orm.web;

import com.example.orm.entity.Enrollment;
import com.example.orm.service.EnrollmentService;
import com.example.orm.web.dto.EnrollRequest;
import com.example.orm.web.dto.EnrollmentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/courses")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/{courseId}/enroll")
    public EnrollmentResponse enroll(@PathVariable Long courseId, @RequestBody @Valid EnrollRequest req) {
        Enrollment e = enrollmentService.enroll(courseId, req.studentId());
        return new EnrollmentResponse(
                e.getId(),
                e.getCourse() != null ? e.getCourse().getId() : null,
                e.getStudent() != null ? e.getStudent().getId() : null,
                e.getEnrollDate(),
                e.getStatus()
        );
    }
}
