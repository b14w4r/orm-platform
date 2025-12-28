-- Minimal demo data for the 3 scenarios (enroll / submit assignment / take quiz)

INSERT INTO users (id, name, email, role) VALUES
  (1, 'Teacher One', 'teacher1@example.com', 'TEACHER'),
  (2, 'Student One', 'student1@example.com', 'STUDENT')
ON CONFLICT DO NOTHING;

INSERT INTO categories (id, name) VALUES
  (1, 'Programming')
ON CONFLICT DO NOTHING;

INSERT INTO courses (id, title, description, duration, start_date, category_id, teacher_id) VALUES
  (1, 'Intro to Java', 'Basics of Java', '2 weeks', '2025-01-01', 1, 1)
ON CONFLICT DO NOTHING;

INSERT INTO modules (id, course_id, title, order_index, description) VALUES
  (1, 1, 'Module 1', 1, 'Getting started')
ON CONFLICT DO NOTHING;

INSERT INTO lessons (id, module_id, title, content, video_url) VALUES
  (1, 1, 'Lesson 1', 'Hello JPA', NULL)
ON CONFLICT DO NOTHING;

INSERT INTO assignments (id, lesson_id, module_id, title, description, due_date, max_score) VALUES
  (1, 1, 1, 'HW1', 'Submit a short text', '2025-02-01', 100)
ON CONFLICT DO NOTHING;

INSERT INTO quizzes (id, module_id, course_id, title, time_limit) VALUES
  (1, 1, 1, 'Quiz 1', 10)
ON CONFLICT DO NOTHING;

INSERT INTO questions (id, quiz_id, text, type) VALUES
  (1, 1, '2 + 2 = ?', 'SINGLE_CHOICE'),
  (2, 1, 'Select Java keywords', 'MULTIPLE_CHOICE')
ON CONFLICT DO NOTHING;

INSERT INTO answer_options (id, question_id, text, is_correct) VALUES
  (1, 1, '3', false),
  (2, 1, '4', true),
  (3, 2, 'class', true),
  (4, 2, 'banana', false)
ON CONFLICT DO NOTHING;
