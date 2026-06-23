package food_ordering_system.service;

import food_ordering_system.dto.MenuDto;
import food_ordering_system.response.PageResponse;
import food_ordering_system.response.Response;

import java.util.List;

public interface MenuService {
    Response<MenuDto> createMenu(MenuDto dto);
    Response<PageResponse<MenuDto>> getAllMenus(Long categoryId, String search,
                                                int page, int size, String sort);
    Response<MenuDto> getMenuById(Long id);
    Response<MenuDto> updateMenu(Long id, MenuDto dto);
    Response<Void> deleteMenu(Long id);
}