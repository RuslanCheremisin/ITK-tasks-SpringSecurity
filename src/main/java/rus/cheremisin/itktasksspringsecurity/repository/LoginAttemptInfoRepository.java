package rus.cheremisin.itktasksspringsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rus.cheremisin.itktasksspringsecurity.entity.LoginAttemptInfo;

import java.util.Optional;

@Repository
public interface LoginAttemptInfoRepository extends JpaRepository<LoginAttemptInfo, Long> {
    Optional<LoginAttemptInfo> findByUser_Username(String userUsername);
}
