package rus.cheremisin.itktasksspringsecurity.service.impl;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import rus.cheremisin.itktasksspringsecurity.DTO.AuthRequest;
import rus.cheremisin.itktasksspringsecurity.DTO.AuthResponse;
import rus.cheremisin.itktasksspringsecurity.config.security.JwtUtils;
import rus.cheremisin.itktasksspringsecurity.service.AuthService;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    AuthenticationManager authenticationManager;
    UserDetailsServiceImpl userDetailsService;
    JwtUtils jwtUtils;
    static Logger log = LoggerFactory.getLogger(AuthService.class);

    @Override
    public AuthResponse authenticateAndGenerateTokens(@Valid AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
        ));
        UserDetails user = userDetailsService.loadUserByUsername(request.username());
        String accessToken = jwtUtils.generateToken(user);
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("tokenType", "refresh");
        claims.put("userId", user.getUsername());
        claims.put("roles", user.getAuthorities());

        String refreshToken = jwtUtils.generateRefreshToken(
                claims,
                user
        );
        log.info("SUCCESSFUL TOKEN GENERATION for user: {}", request.username());
        return new AuthResponse(accessToken, refreshToken);
    }

}
