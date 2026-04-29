package rus.cheremisin.itktasksspringsecurity.config.exceptionHandling;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rus.cheremisin.itktasksspringsecurity.DTO.ErrorResponse;

import javax.security.auth.login.AccountLockedException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseFactory.getUnauthorizedResponse(e, request));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseFactory.getUnauthorizedResponse(e, request));
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseFactory.getNotFoundResponse(e, request));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ErrorResponseFactory.getUnauthorizedResponse(e, request));
    }

    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<?> handleAccountLockedException(AccountLockedException e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ErrorResponseFactory.getForbiddenResponse(e, request));
    }

    private static class ErrorResponseFactory {

        /**
         * Создает {@link ErrorResponse} для ошибок типа "ресурс не найден" (HTTP 404).
         *
         * @param e       исключение, содержащее описание ошибки
         * @param request текущий HTTP-запрос
         * @return объект {@link ErrorResponse} с кодом 404
         */
        private static ErrorResponse getNotFoundResponse(Exception e, HttpServletRequest request) {
            return new ErrorResponse(
                    HttpStatus.NOT_FOUND,
                    "404",
                    e.getMessage(),
                    LocalDateTime.now(),
                    getRequestInfo(request));
        }

        /**
         * Создает {@link ErrorResponse} для ошибок типа "некорректный запрос" (HTTP 400).
         *
         * @param e       исключение, содержащее описание ошибки
         * @param request текущий HTTP-запрос
         * @return объект {@link ErrorResponse} с кодом 400
         */
        private static ErrorResponse getBadRequestResponse(Exception e, HttpServletRequest request) {
            return new ErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    "400",
                    e.getMessage(),
                    LocalDateTime.now(),
                    getRequestInfo(request));
        }

        private static ErrorResponse getUnauthorizedResponse(Exception e, HttpServletRequest request) {
            return new ErrorResponse(
                    HttpStatus.UNAUTHORIZED,
                    "401",
                    e.getMessage(),
                    LocalDateTime.now(),
                    getRequestInfo(request));
        }
        private static ErrorResponse getForbiddenResponse(Exception e, HttpServletRequest request) {
            return new ErrorResponse(
                    HttpStatus.FORBIDDEN,
                    "403",
                    e.getMessage(),
                    LocalDateTime.now(),
                    getRequestInfo(request));
        }

        /**
         * Создает {@link ErrorResponse} для внутренних ошибок сервера (HTTP 500).
         *
         * @param e       исключение, вызвавшее ошибку
         * @param request текущий HTTP-запрос
         * @return объект {@link ErrorResponse} с кодом 500
         */
        private static ErrorResponse getInternalServerErrorResponse(Exception e, HttpServletRequest request) {
            return new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "500",
                    e.getMessage(),
                    LocalDateTime.now(),
                    getRequestInfo(request));
        }

        /**
         * Формирует строковое представление информации о текущем HTTP-запросе.
         *
         * <p>Включает в себя HTTP-метод (например, GET, POST) и URI запроса.
         * Используется для логирования или добавления контекста в ответы об ошибках.</p>
         *
         * <p>Пример результата:
         * <pre>
         * Method: GET; Request URI: /employees/42
         * </pre>
         * </p>
         *
         * @param request текущий {@link HttpServletRequest}, из которого извлекаются данные запроса
         * @return строка с методом и URI запроса
         */

        private static String getRequestInfo(HttpServletRequest request) {
            return new StringBuilder("Method: ")
                    .append(request.getMethod())
                    .append("; Request URI: ")
                    .append(request.getRequestURI())
                    .toString();
        }
    }
}
