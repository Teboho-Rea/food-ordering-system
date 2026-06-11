package food_ordering_system.exception;

// Custom exceptions are used to handle application-specific errors clearly.
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String message) {
        super(message);
    }
}