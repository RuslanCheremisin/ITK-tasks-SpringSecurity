package rus.cheremisin.itktasksspringsecurity.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rus.cheremisin.itktasksspringsecurity.DTO.UserCreateRequest;
import rus.cheremisin.itktasksspringsecurity.DTO.UserDTO;
import rus.cheremisin.itktasksspringsecurity.entity.User;
import rus.cheremisin.itktasksspringsecurity.mapper.UserMapper;
import rus.cheremisin.itktasksspringsecurity.repository.UserRepository;
import rus.cheremisin.itktasksspringsecurity.service.RoleService;
import rus.cheremisin.itktasksspringsecurity.service.UserService;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository dao;
    UserMapper mapper;
    RoleService roleService;

    @Override
    public UserDTO findById(Long id) {
        return mapper.toDto(dao.findById(id).orElseThrow(() -> new EntityNotFoundException("no user with such id")));
    }

    @Override
    public UserDTO addUser(UserCreateRequest request) {

        User user = null;
        user.addRole(roleService.getRoleByName("USER"));
        return mapper.toDto(dao.save(user));
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO dto) {
        User user = dao.findById(id).orElseThrow(() -> new EntityNotFoundException("no user with such id"));
        return mapper.toDto(mapper.mergeToEntity(dto, user));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return mapper.toDtoList(dao.findAll());
    }

    @Override
    public void deleteUser(Long id) {
        User userToDelete = dao.findById(id).orElseThrow(() -> new EntityNotFoundException("no user with such id"));
        dao.delete(userToDelete);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = dao.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("no user with such email"));
        return mapper.toDto(user);
    }

    @Override
    public UserDTO addOAuth2OrTGUser(UserDTO userDTO) {
        User user = dao.save(mapper.toEntity(userDTO));
        return mapper.toDto(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return dao.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("no user with such username"));
    }

    @Override
    public User getCurrentAuthUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            if (auth.getPrincipal() instanceof UserDetails) {
                return (User) auth.getPrincipal();
            } else if (auth.getPrincipal() instanceof OAuth2User) {
                String username = "";
                Map<String, Object> attributes = ((OAuth2User) auth.getPrincipal()).getAttributes();
                if (attributes.containsKey("default_email")) {
                    username = (String) attributes.get("default_email");
                } else if (attributes.containsKey("email")) {
                    username = (String) attributes.get("email");
                }
                UserDTO dto = getUserByUsername(username);
                return mapper.toEntity(dto);

            } else {
                throw new NullPointerException("current principal is null");
            }
        } else {
            throw new RuntimeException("there are no authenticated user");
        }
    }
}
