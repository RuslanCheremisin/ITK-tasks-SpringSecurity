package rus.cheremisin.itktasksspringsecurity.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserCreateRequest(
        @NotNull @NotBlank String firstName,
        @NotNull @NotBlank String lastName,

        @NotNull @NotBlank
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        String email,

        /**
         * Username должен содержать 3–20 символов, латиница + цифры, можно '_' и '.', без пробелов
         */
        @NotNull @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9._]{3,20}$")
        String username,

        /**
         * Password должен содержать минимум 8 символов и включать хотя бы 1 букву, 1 цифру и 1 спецсимвол
         */
        @NotNull @NotBlank
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&]).{8,}$")
        String password
) {}
