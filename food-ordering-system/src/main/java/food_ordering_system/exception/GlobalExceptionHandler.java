package food_ordering_system.exception;

import food_ordering_system.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

// Handles all exceptions thrown across all controllers.
// Returns structured Response<T> JSON instead of Spring's default error page.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles CategoryNotFoundException -> 404
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Response<Void>> handleNotFound(
            CategoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Response.error(404, ex.getMessage())
        );
    }

    // Handles @Valid failures -> 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Void>> handleValidation(
            MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Response.error(400, message)
        );
    }
}