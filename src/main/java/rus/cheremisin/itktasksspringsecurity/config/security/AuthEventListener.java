package rus.cheremisin.itktasksspringsecurity.config.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import rus.cheremisin.itktasksspringsecurity.entity.LoginAttemptInfo;
import rus.cheremisin.itktasksspringsecurity.entity.User;
import rus.cheremisin.itktasksspringsecurity.repository.UserRepository;
import rus.cheremisin.itktasksspringsecurity.service.AuthService;
import rus.cheremisin.itktasksspringsecurity.service.LoginAttemptInfoService;

import javax.security.auth.login.AccountLockedException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthEventListener {

    UserRepository userRepository;
    LoginAttemptInfoService loginAttemptInfoService;
    static Logger log = LoggerFactory.getLogger(AuthEventListener.class);


    @EventListener
    @Transactional(noRollbackFor = BadCredentialsException.class)
    public void handleBadCredentials(AuthenticationFailureBadCredentialsEvent event) {
        String username = (String) event.getAuthentication().getPrincipal();
        LoginAttemptInfo info = loginAttemptInfoService.getByUsername(username);
        info.setAttempts(info.getAttempts() + 1);
        info.setLastFailureAt(LocalDateTime.now());
        if (info.getAttempts() > 4) {
            User user = userRepository.getUserByUsername(username);
            user.setAccountAsLocked(true);
            info.setLockUntil(LocalDateTime.now().plusHours(1));
            userRepository.save(user);
        }
        loginAttemptInfoService.save(info);
        log.warn("FAILED_LOGIN user={}", username);
        throw new BadCredentialsException("Bad credentials!");
    }

    @EventListener
    @Transactional(noRollbackFor = AccountLockedException.class)
    public void handleAuthSuccess(AuthenticationSuccessEvent event) throws AccountLockedException {
        User principal = (User) event.getAuthentication().getPrincipal();
        String username = principal.getUsername();
        LoginAttemptInfo info = loginAttemptInfoService.getByUsername(username);
        LocalDateTime lockUntil = info.getLockUntil();
        if (lockUntil == null || lockUntil.isBefore(LocalDateTime.now())) {
            User user = userRepository.getUserByUsername(username);
            user.setAccountAsLocked(false);
            userRepository.save(user);
            info.setAttempts(0);
            loginAttemptInfoService.save(info);
            log.info("SUCCESS_LOGIN user={}", username);
        } else {
            log.warn("ACCOUNT_LOCKED user={}", username);
            throw new AccountLockedException(username + " account is locked until " + lockUntil);
        }
    }

}
