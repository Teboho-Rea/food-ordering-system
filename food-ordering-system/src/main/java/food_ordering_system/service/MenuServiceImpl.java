package food_ordering_system.service;

import food_ordering_system.dto.MenuDto;
import food_ordering_system.entity.Category;
import food_ordering_system.entity.Menu;
import food_ordering_system.exception.CategoryNotFoundException;
import food_ordering_system.exception.MenuNotFoundException;
import food_ordering_system.repository.CategoryRepository;
import food_ordering_system.repository.MenuRepository;
import food_ordering_system.response.PageResponse;
import food_ordering_system.response.Response;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import food_ordering_system.repository.CategoryRepository;
import food_ordering_system.repository.MenuRepository;
import food_ordering_system.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// Contains all business logic for Menu operations.
// Converts between Menu entities and MenuDto objects.
@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Response<MenuDto> createMenu(MenuDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Category not found with id: " + dto.getCategoryId()));
        Menu saved = menuRepository.save(mapToEntity(dto, category));
        return Response.success("Menu created", mapToDto(saved));
    }

    @Override
    public Response<PageResponse<MenuDto>> getAllMenus(Long categoryId,
                                                       String search,
                                                       int page,
                                                       int size,
                                                       String sort) {
        // Build sort direction
        Sort sortOrder = Sort.by(Sort.Direction.ASC, "id");
        if (sort != null && !sort.isBlank()) {
            String[] parts = sort.split(",");
            String field = parts[0].trim();
            Sort.Direction direction = parts.length > 1 &&
                    parts[1].trim().equalsIgnoreCase("desc")
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
            sortOrder = Sort.by(direction, field);
        }

        Pageable pageable = PageRequest.of(page, size, sortOrder);

        // Build dynamic filter
        Specification<Menu> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (categoryId != null) {
                predicates.add(cb.equal(
                        root.get("category").get("id"), categoryId));
            }

            if (search != null && !search.isBlank()) {
                predicates.add(cb.like(
                        cb.lower(root.get("name")),
                        "%" + search.toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Menu> resultPage = menuRepository.findAll(spec, pageable);

        PageResponse<MenuDto> pageResponse = PageResponse.<MenuDto>builder()
                .content(resultPage.getContent().stream()
                        .map(this::mapToDto).toList())
                .totalElements(resultPage.getTotalElements())
                .totalPages(resultPage.getTotalPages())
                .number(resultPage.getNumber())
                .size(resultPage.getSize())
                .first(resultPage.isFirst())
                .last(resultPage.isLast())
                .build();

        return Response.success("Menus retrieved", pageResponse);
    }

    @Override
    public Response<MenuDto> getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new MenuNotFoundException(
                        "Menu not found with id: " + id));
        return Response.success("Menu retrieved", mapToDto(menu));
    }

    @Override
    public Response<MenuDto> updateMenu(Long id, MenuDto dto) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new MenuNotFoundException(
                        "Menu not found with id: " + id));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Category not found with id: " + dto.getCategoryId()));

        menu.setName(dto.getName());
        menu.setDescription(dto.getDescription());
        menu.setPrice(dto.getPrice());
        menu.setImageUrl(dto.getImageUrl());
        menu.setCategory(category);

        return Response.success("Menu updated", mapToDto(menuRepository.save(menu)));
    }

    @Override
    public Response<Void> deleteMenu(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new MenuNotFoundException(
                        "Menu not found with id: " + id));
        menuRepository.delete(menu);
        return Response.success("Menu deleted", null);
    }

    // Converts a Menu entity to a MenuDto

        Menu menu = mapToEntity(dto, category);
        Menu savedMenu = menuRepository.save(menu);

        return Response.success("Menu created", mapToDto(savedMenu));
    }

    @Override
    public Response<List<MenuDto>> getAllMenus() {
        List<MenuDto> menus = menuRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();

        return Response.success("Menus retrieved", menus);
    }

    @Override
    public Response<MenuDto> getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Menu not found with id: " + id));

        return Response.success("Menu retrieved", mapToDto(menu));
    }

    // Converts a Menu entity into a MenuDto for the API response
    private MenuDto mapToDto(Menu menu) {
        MenuDto dto = new MenuDto();
        dto.setId(menu.getId());
        dto.setName(menu.getName());
        dto.setDescription(menu.getDescription());
        dto.setPrice(menu.getPrice());
        dto.setImageUrl(menu.getImageUrl());
        if (menu.getCategory() != null) {
            dto.setCategoryId(menu.getCategory().getId());
            dto.setCategoryName(menu.getCategory().getName());
        }
        return dto;
    }

    // Converts an incoming MenuDto and resolved Category to a Menu entity
    // Converts an incoming MenuDto plus its resolved Category into a Menu entity
    private Menu mapToEntity(MenuDto dto, Category category) {
        return Menu.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .imageUrl(dto.getImageUrl())
                .category(category)
                .build();
    }
}