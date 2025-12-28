package com.example.orm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CategoryMvcTests {

    @Autowired
    MockMvc mvc;

    @Test
    void createAndListCategoriesWorksOnH2() throws Exception {
        mvc.perform(post("/api/categories")
                .contentType("application/json")
                .content("{\"name\":\"Databases\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        mvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
