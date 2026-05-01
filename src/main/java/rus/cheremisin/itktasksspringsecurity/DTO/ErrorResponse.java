package rus.cheremisin.itktasksspringsecurity.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(
        @NotNull
        HttpStatus status,
        @NotNull
        @NotBlank String errorCode,
        @NotNull
        @NotBlank String message,
        @NotNull
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime timestamp,
        @NotNull String path) {
}
