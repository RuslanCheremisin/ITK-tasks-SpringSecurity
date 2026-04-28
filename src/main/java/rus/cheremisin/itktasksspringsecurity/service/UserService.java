package rus.cheremisin.itktasksspringsecurity.service;

import rus.cheremisin.itktasksspringsecurity.DTO.UserCreateRequest;
import rus.cheremisin.itktasksspringsecurity.DTO.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO addUser(UserCreateRequest request);

    UserDTO updateUser(Long id, UserDTO user);

    List<UserDTO> getAllUsers();

    void deleteUser(Long id);

    UserDTO findById(Long userId);
}
