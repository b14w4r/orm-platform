package com.example.orm.service;

import com.example.orm.entity.Course;
import com.example.orm.entity.Enrollment;
import com.example.orm.entity.User;
import com.example.orm.enums.EnrollmentStatus;
import com.example.orm.repository.CourseRepository;
import com.example.orm.repository.EnrollmentRepository;
import com.example.orm.repository.UserRepository;
import com.example.orm.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Transactional
    public Enrollment enroll(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course not found: " + courseId));
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("User not found: " + studentId));

        return enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseGet(() -> enrollmentRepository.save(
                        Enrollment.builder()
                                .course(course)
                                .student(student)
                                .enrollDate(OffsetDateTime.now())
                                .status(EnrollmentStatus.ACTIVE)
                                .build()
                ));
    }
}
