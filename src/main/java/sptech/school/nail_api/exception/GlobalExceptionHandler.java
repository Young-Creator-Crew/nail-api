package sptech.school.nail_api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sptech.school.nail_api.dto.exception.StandardErrorResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataBaseAccessException.class)
    public ResponseEntity<StandardErrorResponse> handleDataBaseAccess(DataBaseAccessException exception, HttpServletRequest request) {
        exception.printStackTrace();

        StandardErrorResponse error = new StandardErrorResponse(
                LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
                500,
                "Internal Server Error",
                "Internal error communicating with the database",
                request.getRequestURI()
        );

        return ResponseEntity.status(500).body(error);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<StandardErrorResponse> handleInvalidCredentials(InvalidCredentialsException exception, HttpServletRequest request) {
        exception.printStackTrace();

        StandardErrorResponse error = new StandardErrorResponse(
                LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
                401,
                "Unauthorized",
                "Request with invalid credentials",
                request.getRequestURI()
        );

        return ResponseEntity.status(401).body(error);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<StandardErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException exception, HttpServletRequest request) {
        exception.printStackTrace();

        StandardErrorResponse error = new StandardErrorResponse(
                LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
                409,
                "Conflict",
                "User already exists in the database",
                request.getRequestURI()
        );

        return ResponseEntity.status(409).body(error);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<StandardErrorResponse> handleUserNotFound(UserNotFoundException exception, HttpServletRequest request) {
        exception.printStackTrace();

        StandardErrorResponse error = new StandardErrorResponse(
                LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
                404,
                "Not Found",
                "User not found in the database",
                request.getRequestURI()
        );

        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardErrorResponse> handleValidationErrors(MethodArgumentNotValidException exception, HttpServletRequest request) {

        StandardErrorResponse error = new StandardErrorResponse(
                LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
                400,
                "Bad Request",
                "Invalid data, check the fields", // Mensagem estática e direta
                request.getRequestURI()
        );

        return ResponseEntity.status(400).body(error);
    }

    public ResponseEntity<StandardErrorResponse> handleGenericException(Exception exception, HttpServletRequest request) {
        exception.printStackTrace();

        StandardErrorResponse error = new StandardErrorResponse(
                LocalDateTime.now(ZoneId.of("America/Sao_Paulo")),
                500,
                "Internal Server Error",
                "An unexpected error occurred on the server",
                request.getRequestURI()
        );

        return ResponseEntity.status(500).body(error);
    }

}