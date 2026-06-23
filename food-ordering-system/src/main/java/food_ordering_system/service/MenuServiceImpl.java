package food_ordering_system.service;

import food_ordering_system.dto.MenuDto;
import food_ordering_system.entity.Category;
import food_ordering_system.entity.Menu;
import food_ordering_system.exception.CategoryNotFoundException;
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