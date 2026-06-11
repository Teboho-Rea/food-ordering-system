package food_ordering_system.dto;

import lombok.Data;

// DTO means Data Transfer Object.
// It is used to send category data without exposing the full database entity.
@Data
public class CategoryDto {

    private Long id;
    private String name;
}