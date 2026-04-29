package rus.cheremisin.itktasksspringsecurity.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rus.cheremisin.itktasksspringsecurity.entity.Role;
import rus.cheremisin.itktasksspringsecurity.service.RoleService;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/roles")
@EnableMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true
)
public class RoleController {

    RoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('SUPER-ADMIN')")
    public ResponseEntity<Set<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping("/by-ids")
    @PreAuthorize("hasRole('SUPER-ADMIN')")
    public ResponseEntity<Set<Role>> getRolesByIds(@RequestBody Set<Long> roleIds) {
        return ResponseEntity.ok(roleService.getRolesByIds(roleIds));
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER-ADMIN')")
    public ResponseEntity<Void> addRole(@Valid @RequestBody Role role) {
        roleService.addRole(role);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{role-name}")
    @PreAuthorize("hasRole('SUPER-ADMIN')")
    public ResponseEntity<Role> getRoleByName(@PathVariable("role-name") String name) {
        return ResponseEntity.ok(roleService.getRoleByName(name));
    }
}