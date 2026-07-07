package food_ordering_system.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

// DTO for Menu. Used to send and receive menu data through the API
// without exposing the database entity directly.
@Data
public class MenuDto {

    private Long id;

    @NotBlank(message = "Menu name is required")
    private String name;

    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true,
            message = "Price must not be negative")
    private BigDecimal price;

    private String imageUrl;

    @NotNull(message = "categoryId is required")
    private Long categoryId;

    private String categoryName;
}