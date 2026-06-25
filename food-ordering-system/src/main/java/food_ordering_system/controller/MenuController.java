package food_ordering_system.controller;

import food_ordering_system.dto.MenuDto;
import food_ordering_system.response.PageResponse;
import food_ordering_system.response.Response;
import food_ordering_system.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    // POST /api/menu
    @PostMapping
    public ResponseEntity<Response<MenuDto>> create(
            @RequestBody @Valid MenuDto dto) {
        return ResponseEntity.ok(menuService.createMenu(dto));
    }

    // GET /api/menu — supports categoryId, search, page, size, sort
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

    // GET /api/menu/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Response<MenuDto>> getById(
            @PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }

    // PUT /api/menu/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Response<MenuDto>> update(
            @PathVariable Long id,
            @RequestBody @Valid MenuDto dto) {
        return ResponseEntity.ok(menuService.updateMenu(id, dto));
    }

    // DELETE /api/menu/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> delete(
            @PathVariable Long id) {
        return ResponseEntity.ok(menuService.deleteMenu(id));
    }
    @GetMapping
    public ResponseEntity<Response<List<MenuDto>>> all() {
        return ResponseEntity.ok(menuService.getAllMenus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<MenuDto>> byId(
            @PathVariable Long id) {
        return ResponseEntity.ok(menuService.getMenuById(id));
    }
}