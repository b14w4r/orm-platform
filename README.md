# ORM learning project (Spring Boot + JPA)


## Quick start

```bash
docker compose up -d
mvn spring-boot:run
```

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


## Tests

```bash
mvn test
```
