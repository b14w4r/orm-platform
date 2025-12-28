package com.example.orm.web;

import com.example.orm.service.QuizService;
import com.example.orm.web.dto.QuizResultResponse;
import com.example.orm.web.dto.TakeQuizRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    @PostMapping("/{quizId}/take")
    public QuizResultResponse take(@PathVariable Long quizId, @RequestBody @Valid TakeQuizRequest req) {
        return quizService.takeQuiz(quizId, req);
    }
}
