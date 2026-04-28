package rus.cheremisin.itktasksspringsecurity.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record AuthRequest(@NotNull @NotBlank
                          @Pattern(regexp = "^[a-zA-Z0-9._]{3,20}$")
                          String username,
                          @NotNull @NotBlank
                          @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&]).{8,}$")
                          String password) {
}
