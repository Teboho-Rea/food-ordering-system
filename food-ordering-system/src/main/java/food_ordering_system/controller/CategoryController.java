package food_ordering_system.controller;

import food_ordering_system.dto.CategoryDto;
import food_ordering_system.response.Response;
import food_ordering_system.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

// Handles all incoming HTTP requests for the Category resource.
// Delegates business logic to CategoryService.
@Tag(name = "Categories", description = "Category management endpoints")
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Get all categories")
    @GetMapping
    public ResponseEntity<Response<List<CategoryDto>>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(
                Response.success("Categories retrieved", categories)
        );
    }

    @Operation(summary = "Get category by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Response<CategoryDto>> getCategoryById(
            @PathVariable Long id) {
        CategoryDto dto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(
                Response.success("Category retrieved", dto)
        );
    }

    @Operation(summary = "Create a category", description = "ADMIN only")
    @PostMapping
    public ResponseEntity<Response<CategoryDto>> addCategory(
            @RequestBody @Valid CategoryDto dto) {
        CategoryDto created = categoryService.addCategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Response.<CategoryDto>builder()
                        .statusCode(201)
                        .message("Category created")
                        .data(created)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }

    @Operation(summary = "Update a category", description = "ADMIN only")
    @PutMapping("/{id}")
    public ResponseEntity<Response<CategoryDto>> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid CategoryDto dto) {
        CategoryDto updated = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(
                Response.success("Category updated", updated)
        );
    }

    @Operation(summary = "Delete a category", description = "ADMIN only")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteCategory(
            @PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(
                Response.success("Category deleted", null)
        );
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody @Valid CategoryDto dto) {
        CategoryDto savedCategory = categoryService.addCategory(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryDto dto) {
        return categoryService.updateCategory(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}