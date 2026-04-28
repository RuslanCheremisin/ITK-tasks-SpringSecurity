package rus.cheremisin.itktasksspringsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import rus.cheremisin.itktasksspringsecurity.DTO.UserDTO;
import rus.cheremisin.itktasksspringsecurity.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails getUserByUsername(String username);
}
