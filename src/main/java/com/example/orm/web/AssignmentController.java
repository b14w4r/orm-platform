package com.example.orm.web;

import com.example.orm.service.AssignmentCrudService;
import com.example.orm.service.SubmissionService;
import com.example.orm.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentCrudService assignmentCrudService;
    private final SubmissionService submissionService;

    @PostMapping
    public AssignmentResponse create(@RequestBody @Valid CreateAssignmentRequest req) {
        return assignmentCrudService.create(req);
    }

    @GetMapping("/{id}")
    public AssignmentResponse get(@PathVariable Long id) {
        return assignmentCrudService.get(id);
    }

    @GetMapping
    public List<AssignmentResponse> list() {
        return assignmentCrudService.list();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        assignmentCrudService.delete(id);
    }

    @PostMapping("/{assignmentId}/submit")
    public SubmissionResponse submit(@PathVariable Long assignmentId, @RequestBody @Valid SubmitAssignmentRequest req) {
        return submissionService.submit(assignmentId, req);
    }
}
