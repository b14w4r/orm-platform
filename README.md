# ORM learning project (Spring Boot + JPA)

Минимальный набор под ТЗ: **3 сценария** + CRUD для **2–3 сущностей**.

## Quick start

```bash
docker compose up -d
mvn spring-boot:run
```

Swagger UI: `/swagger-ui.html`.

## Main endpoints

CRUD:
- POST /api/users
- GET /api/users
- GET /api/users/{id}
- DELETE /api/users/{id}
- POST /api/courses
- GET /api/courses
- GET /api/courses/{id}
- DELETE /api/courses/{id}
- POST /api/categories
- GET /api/categories
- GET /api/categories/{id}
- DELETE /api/categories/{id}
- POST /api/assignments
- GET /api/assignments
- GET /api/assignments/{id}
- DELETE /api/assignments/{id}

Scenarios:
- POST /api/courses/{courseId}/enroll
- POST /api/assignments/{assignmentId}/submit
- POST /api/quizzes/{quizId}/take

## Demo data

При старте подхватывается `data.sql` (создаёт users/categories/course/module/lesson/assignment/quiz/questions/options).

## Tests

```bash
mvn test
```

Тесты используют Testcontainers и поднимают PostgreSQL автоматически.
