package rus.cheremisin.itktasksspringsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rus.cheremisin.itktasksspringsecurity.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
