package site.thanhtungle.bookingservice.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import site.thanhtungle.commons.exception.CustomNotFoundException;
import site.thanhtungle.commons.model.response.error.ApiErrorResponse;
import site.thanhtungle.commons.model.response.error.ApiMultipleErrorResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundException(Exception ex, WebRequest webRequest) {
        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                webRequest.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // handle validation exception
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiMultipleErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, WebRequest webRequest) {
        List<Map<String, String>> errorMessageList = ex.getConstraintViolations()
                .stream()
                .map(violation -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("field", violation.getPropertyPath().toString());
                    errorMap.put("message", violation.getMessage());
                    return errorMap;
                })
                .collect(Collectors.toList());
        ApiMultipleErrorResponse response = new ApiMultipleErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errorMessageList,
                webRequest.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // handle validation exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiMultipleErrorResponse> handleConstraintViolationException(MethodArgumentNotValidException ex, WebRequest webRequest) {
        List<Map<String, String>> errorMessageList = ex.getBindingResult().getFieldErrors().stream().map(error -> {
            Map<String, String> errorMap = new HashMap<>();
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errorMap.put("field", fieldName);
            errorMap.put("message", errorMessage);
            return errorMap;
        }).collect(Collectors.toList());

        ApiMultipleErrorResponse response = new ApiMultipleErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errorMessageList,
                webRequest.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGlobalException(Exception ex, WebRequest webRequest) {
        ApiErrorResponse response = new ApiErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                webRequest.getDescription(false)
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
