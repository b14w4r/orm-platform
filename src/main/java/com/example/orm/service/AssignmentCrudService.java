package com.example.orm.service;

import com.example.orm.entity.Assignment;
import com.example.orm.entity.Lesson;
import com.example.orm.entity.Module;
import com.example.orm.repository.AssignmentRepository;
import com.example.orm.repository.LessonRepository;
import com.example.orm.repository.ModuleRepository;
import com.example.orm.web.dto.AssignmentResponse;
import com.example.orm.web.dto.CreateAssignmentRequest;
import com.example.orm.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentCrudService {

    private final AssignmentRepository assignmentRepository;
    private final LessonRepository lessonRepository;
    private final ModuleRepository moduleRepository;

    public AssignmentResponse create(CreateAssignmentRequest req) {
        Lesson lesson = lessonRepository.findById(req.lessonId())
                .orElseThrow(() -> new NotFoundException("Lesson not found: " + req.lessonId()));

        Module module = null;
        if (req.moduleId() != null) {
            module = moduleRepository.findById(req.moduleId())
                    .orElseThrow(() -> new NotFoundException("Module not found: " + req.moduleId()));
        }

        Assignment a = Assignment.builder()
                .lesson(lesson)
                .module(module)
                .title(req.title())
                .description(req.description())
                .dueDate(req.dueDate())
                .maxScore(req.maxScore())
                .build();

        return toResponse(assignmentRepository.save(a));
    }

    public AssignmentResponse get(Long id) {
        Assignment a = assignmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assignment not found: " + id));
        return toResponse(a);
    }

    public List<AssignmentResponse> list() {
        return assignmentRepository.findAll().stream().map(this::toResponse).toList();
    }

    public void delete(Long id) {
        assignmentRepository.delete(assignmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assignment not found: " + id)));
    }

    private AssignmentResponse toResponse(Assignment a) {
        Long lessonId = a.getLesson() != null ? a.getLesson().getId() : null;
        Long moduleId = a.getModule() != null ? a.getModule().getId() : null;
        return new AssignmentResponse(a.getId(), a.getTitle(), a.getDescription(), a.getDueDate(), a.getMaxScore(), lessonId, moduleId);
    }
}
