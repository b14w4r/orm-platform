package com.example.orm.service;

import com.example.orm.entity.Category;
import com.example.orm.repository.CategoryRepository;
import com.example.orm.web.dto.CategoryResponse;
import com.example.orm.web.dto.CreateCategoryRequest;
import com.example.orm.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse create(CreateCategoryRequest req) {
        Category c = Category.builder().name(req.name()).build();
        Category saved = categoryRepository.save(c);
        return new CategoryResponse(saved.getId(), saved.getName());
    }

    public CategoryResponse get(Long id) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found: " + id));
        return new CategoryResponse(c.getId(), c.getName());
    }

    public List<CategoryResponse> list() {
        return categoryRepository.findAll().stream()
                .map(c -> new CategoryResponse(c.getId(), c.getName()))
                .toList();
    }

    public void delete(Long id) {
        categoryRepository.delete(categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found: " + id)));
    }
}
