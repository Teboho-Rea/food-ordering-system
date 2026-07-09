package food_ordering_system.exception;

import food_ordering_system.response.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// This class handles errors and sends clear responses to the client.
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNotFound(CategoryNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    public ResponseEntity<Response<Void>> handleCategoryNotFound(
            CategoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Response.error(404, ex.getMessage())
        );
    }

    @ExceptionHandler(MenuNotFoundException.class)
    public ResponseEntity<Response<Void>> handleMenuNotFound(
            MenuNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Response.error(404, ex.getMessage())
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Response<Void>> handleConflict(
            ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Response.error(409, ex.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Response<Void>> handleDataIntegrity(
            DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                Response.error(409, "Cannot delete: this record is still referenced by other data")
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Void>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                Response.error(500, "An unexpected error occurred: " + ex.getMessage())
        );
    }
}