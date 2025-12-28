package com.example.orm.service;

import com.example.orm.entity.*;
import com.example.orm.repository.*;
import com.example.orm.web.dto.QuizResultResponse;
import com.example.orm.web.dto.TakeQuizRequest;
import com.example.orm.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final AnswerOptionRepository answerOptionRepository;
    private final QuestionRepository questionRepository;
    private final QuizSubmissionRepository quizSubmissionRepository;

    @Transactional
    public QuizResultResponse takeQuiz(Long quizId, TakeQuizRequest req) {
        Quiz quiz = quizRepository.findWithQuestionsById(quizId)
                .orElseThrow(() -> new NotFoundException("Quiz not found: " + quizId));
        User student = userRepository.findById(req.studentId())
                .orElseThrow(() -> new NotFoundException("User not found: " + req.studentId()));

        QuizSubmission submission = QuizSubmission.builder()
                .quiz(quiz)
                .student(student)
                .takenAt(OffsetDateTime.now())
                .build();

        int score = 0;
        int total = quiz.getQuestions().size();

        for (Question q : quiz.getQuestions()) {
            Long selectedOptionId = req.answers().get(q.getId());
            if (selectedOptionId == null) {
                continue;
            }

            AnswerOption opt = answerOptionRepository.findById(selectedOptionId)
                    .orElseThrow(() -> new NotFoundException("AnswerOption not found: " + selectedOptionId));

            Question qFromDb = questionRepository.findById(q.getId())
                    .orElseThrow(() -> new NotFoundException("Question not found: " + q.getId()));

            if (opt.getQuestion() == null || !opt.getQuestion().getId().equals(qFromDb.getId())) {
                throw new IllegalArgumentException("Selected option does not belong to question: " + q.getId());
            }

            submission.getAnswers().add(
                    QuizSubmissionAnswer.builder()
                            .quizSubmission(submission)
                            .question(qFromDb)
                            .selectedOption(opt)
                            .build()
            );

            if (opt.isCorrect()) {
                score += 1;
            }
        }

        submission.setScore(score);
        QuizSubmission saved = quizSubmissionRepository.save(submission);

        return new QuizResultResponse(saved.getId(), saved.getScore(), total, saved.getTakenAt());
    }
}
