package food_ordering_system.controller;

import food_ordering_system.dto.MenuDto;
import food_ordering_system.response.PageResponse;
import food_ordering_system.response.Response;
import food_ordering_system.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Menu", description = "Menu item management endpoints")
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "Create a menu item", description = "ADMIN only")
    @PostMapping
    public ResponseEntity<Response<MenuDto>> create(
            @RequestBody @Valid MenuDto dto) {
        return ResponseEntity.ok(menuService.createMenu(dto));
    }

    @Operation(summary = "Get all menus",
            description = "Supports optional filtering by categoryId, search, page, size, sort")
    @GetMapping
    public ResponseEntity<Response<PageResponse<MenuDto>>> getAll(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort) {
        return ResponseEntity.ok(
                menuService.getAllMenus(categoryId, search, page, size, sort));
    }

    @Operation(summary = "Get menu item by ID")
    @GetMapping("/{id}")
    public ResponseEntity<Response<MenuDto>> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }

    @Operation(summary = "Update a menu item", description = "ADMIN only")
    @PutMapping("/{id}")
    public ResponseEntity<Response<MenuDto>> update(
            @PathVariable Long id,
            @RequestBody @Valid MenuDto dto) {
        return ResponseEntity.ok(menuService.updateMenu(id, dto));
    }

    @Operation(summary = "Delete a menu item", description = "ADMIN only")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> delete(
            @PathVariable Long id) {
        return ResponseEntity.ok(menuService.deleteMenu(id));
    }
}