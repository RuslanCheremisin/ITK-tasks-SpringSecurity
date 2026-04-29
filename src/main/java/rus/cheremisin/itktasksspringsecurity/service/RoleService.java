package rus.cheremisin.itktasksspringsecurity.service;

import rus.cheremisin.itktasksspringsecurity.entity.Role;

import java.util.Optional;
import java.util.Set;

public interface RoleService {
    void addRole(Role role);

    Role getRoleByName(String name);

    Set<Role> getAllRoles();

    Set<Role> getRolesByIds(Set<Long> roleIds);
}
