package rus.cheremisin.itktasksspringsecurity.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import rus.cheremisin.itktasksspringsecurity.entity.Role;

import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
public record UserDTO (
    Long id,
    @NotNull
    @NotBlank
    String firstName,
    @NotNull
    @NotBlank
    String lastName,
    String phone,
    String bio,
    @NotNull
    @NotBlank
    String email,
    @NotNull
    @NotBlank
    String username,
    Set<Role> roles){}
