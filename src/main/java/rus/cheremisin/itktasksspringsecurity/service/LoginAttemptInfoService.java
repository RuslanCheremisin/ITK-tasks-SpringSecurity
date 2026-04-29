package rus.cheremisin.itktasksspringsecurity.service;

import rus.cheremisin.itktasksspringsecurity.entity.LoginAttemptInfo;

public interface LoginAttemptInfoService {

    LoginAttemptInfo save(LoginAttemptInfo info);

    LoginAttemptInfo getByUsername(String username);
}
