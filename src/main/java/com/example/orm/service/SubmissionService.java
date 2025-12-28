package com.example.orm.service;

import com.example.orm.entity.Assignment;
import com.example.orm.entity.Submission;
import com.example.orm.entity.User;
import com.example.orm.repository.AssignmentRepository;
import com.example.orm.repository.SubmissionRepository;
import com.example.orm.repository.UserRepository;
import com.example.orm.web.dto.SubmitAssignmentRequest;
import com.example.orm.web.dto.SubmissionResponse;
import com.example.orm.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    @Transactional
    public SubmissionResponse submit(Long assignmentId, SubmitAssignmentRequest req) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new NotFoundException("Assignment not found: " + assignmentId));
        User student = userRepository.findById(req.studentId())
                .orElseThrow(() -> new NotFoundException("User not found: " + req.studentId()));

        Submission s = Submission.builder()
                .assignment(assignment)
                .student(student)
                .submittedAt(OffsetDateTime.now())
                .content(req.content())
                .build();

        Submission saved = submissionRepository.save(s);
        return new SubmissionResponse(
                saved.getId(),
                saved.getAssignment() != null ? saved.getAssignment().getId() : null,
                saved.getStudent() != null ? saved.getStudent().getId() : null,
                saved.getSubmittedAt(),
                saved.getContent(),
                saved.getScore(),
                saved.getFeedback()
        );
    }
}
