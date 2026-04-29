package rus.cheremisin.itktasksspringsecurity.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rus.cheremisin.itktasksspringsecurity.DTO.UserCreateRequest;
import rus.cheremisin.itktasksspringsecurity.DTO.UserDTO;
import rus.cheremisin.itktasksspringsecurity.entity.LoginAttemptInfo;
import rus.cheremisin.itktasksspringsecurity.entity.User;
import rus.cheremisin.itktasksspringsecurity.mapper.UserMapper;
import rus.cheremisin.itktasksspringsecurity.repository.LoginAttemptInfoRepository;
import rus.cheremisin.itktasksspringsecurity.repository.UserRepository;
import rus.cheremisin.itktasksspringsecurity.service.RoleService;
import rus.cheremisin.itktasksspringsecurity.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper mapper;
    RoleService roleService;
    PasswordEncoder encoder;
    LoginAttemptInfoRepository loginAttemptInfoRepository;

    @Override
    public UserDTO findById(Long id) {
        return mapper.toDto(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("no user with such id")));
    }

    @Override
    public UserDTO addUser(UserCreateRequest request) {

        User user = userRepository.save(mapper.fromCreateRequestToEntity(request));
        user.setPassword(encoder.encode(request.password()));
        user.addRole(roleService.getRoleByName("USER"));

        loginAttemptInfoRepository.save(new LoginAttemptInfo(null, 0, null, LocalDateTime.now(), user));

        return mapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("no user with such id"));
        return mapper.toDto(mapper.mergeToEntity(dto, user));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return mapper.toDtoList(userRepository.findAll());
    }

    @Override
    public void deleteUser(Long id) {
        User userToDelete = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("no user with such id"));
        userRepository.delete(userToDelete);
    }


}
