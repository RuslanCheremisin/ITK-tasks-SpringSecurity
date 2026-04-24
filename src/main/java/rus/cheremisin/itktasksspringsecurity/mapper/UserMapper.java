package rus.cheremisin.itktasksspringsecurity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import rus.cheremisin.itktasksspringsecurity.DTO.UserCreateRequest;
import rus.cheremisin.itktasksspringsecurity.DTO.UserDTO;
import rus.cheremisin.itktasksspringsecurity.entity.User;


import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {

    UserDTO toDto(User user);

    User toEntity(UserDTO dto);

    User fromCreateRequestToEntity(UserCreateRequest request);

    List<UserDTO> toDtoList(List<User> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User mergeToEntity(UserDTO dto, @MappingTarget User user);


}
