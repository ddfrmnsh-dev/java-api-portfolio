package com.example.api.portfolio.exception;
import com.example.api.portfolio.dto.ApiResponse;
import com.example.api.portfolio.dto.MetaResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        MetaResponse meta = new MetaResponse("Resource not found", HttpStatus.NOT_FOUND.value(), "error");
        ApiResponse<String> response = new ApiResponse<>(meta, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {
        MetaResponse meta = new MetaResponse("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR.value(), "error");
        ApiResponse<String> response = new ApiResponse<>(meta, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
