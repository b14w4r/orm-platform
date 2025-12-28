package com.example.orm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest
@AutoConfigureMockMvc
class ApiIntegrationTests {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("orm")
            .withUsername("orm")
            .withPassword("orm");

    @DynamicPropertySource
    static void datasourceProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
        registry.add("spring.sql.init.mode", () -> "always");
        registry.add("spring.jpa.defer-datasource-initialization", () -> "true");
        registry.add("spring.jpa.open-in-view", () -> "false");
    }

    @Autowired
    MockMvc mvc;

    @Test
    void enrollScenarioWorks() throws Exception {
        mvc.perform(post("/api/courses/1/enroll")
                .contentType("application/json")
                .content("""
                        {"studentId": 2}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseId").value(1))
                .andExpect(jsonPath("$.studentId").value(2))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void submitAssignmentScenarioWorks() throws Exception {
        mvc.perform(post("/api/assignments/1/submit")
                .contentType("application/json")
                .content("""
                        {"studentId": 2, "content": "my answer"}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.assignmentId").value(1))
                .andExpect(jsonPath("$.studentId").value(2))
                .andExpect(jsonPath("$.content").value("my answer"));
    }

    @Test
    void takeQuizScenarioWorks() throws Exception {
        mvc.perform(post("/api/quizzes/1/take")
                .contentType("application/json")
                .content("""
                        {"studentId": 2, "answers": {"1": 2, "2": 3}}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalQuestions").value(2))
                .andExpect(jsonPath("$.score").value(2));
    }

    @Test
    void categoryCrudWorks() throws Exception {
        mvc.perform(post("/api/categories")
                .contentType("application/json")
                .content("""
                        {"name": "Databases"}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Databases"));

        mvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
