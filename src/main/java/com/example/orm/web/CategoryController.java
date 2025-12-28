package com.example.orm.web;

import com.example.orm.service.CategoryService;
import com.example.orm.web.dto.CategoryResponse;
import com.example.orm.web.dto.CreateCategoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryResponse create(@RequestBody @Valid CreateCategoryRequest req) {
        return categoryService.create(req);
    }

    @GetMapping("/{id}")
    public CategoryResponse get(@PathVariable Long id) {
        return categoryService.get(id);
    }

    @GetMapping
    public List<CategoryResponse> list() {
        return categoryService.list();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
