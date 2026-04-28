package rus.cheremisin.itktasksspringsecurity.service;

import rus.cheremisin.itktasksspringsecurity.DTO.AuthRequest;
import rus.cheremisin.itktasksspringsecurity.DTO.AuthResponse;

public interface AuthService {
    AuthResponse authenticateAndGenerateTokens(AuthRequest request);
}
