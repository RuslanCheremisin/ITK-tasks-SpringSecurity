package rus.cheremisin.itktasksspringsecurity.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import rus.cheremisin.itktasksspringsecurity.entity.Role;

import java.util.Set;

public record UserDTO(
        Long id,
        @NotNull
        @NotBlank String firstName,
        @NotNull
        @NotBlank String lastName,
        String phone,
        String bio,
        @NotNull
        @NotBlank String email,
        @NotNull
        @NotBlank String username,
        Set<Role> roles) {
}
