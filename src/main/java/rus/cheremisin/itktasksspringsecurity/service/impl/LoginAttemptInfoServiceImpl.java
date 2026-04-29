package rus.cheremisin.itktasksspringsecurity.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rus.cheremisin.itktasksspringsecurity.entity.LoginAttemptInfo;
import rus.cheremisin.itktasksspringsecurity.repository.LoginAttemptInfoRepository;
import rus.cheremisin.itktasksspringsecurity.service.LoginAttemptInfoService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginAttemptInfoServiceImpl implements LoginAttemptInfoService {

    LoginAttemptInfoRepository repository;

    @Override
    @Transactional
    public LoginAttemptInfo save(LoginAttemptInfo info) {
        return repository.save(info);
    }

    @Override
    public LoginAttemptInfo getByUsername(String username) {
        Optional<LoginAttemptInfo> optionalInfo = repository.findByUser_Username(username);
        if (optionalInfo.isPresent()) {
            return optionalInfo.get();
        } else {
            throw new EntityNotFoundException("No record of failed attempts to login for user: " + username);
        }
    }
}
