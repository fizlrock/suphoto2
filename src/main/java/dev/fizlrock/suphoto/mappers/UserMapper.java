package dev.fizlrock.suphoto.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.UserDTO;
import org.openapitools.model.UserDTO.RoleEnum;

import dev.fizlrock.suphoto.domain.entity.User;
import dev.fizlrock.suphoto.domain.entity.User.Role;

/**
 * UserMapper
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @ValueMappings({
      @ValueMapping(source = "None", target = "NONE"),
      @ValueMapping(source = "Client", target = "CLIENT"),
      @ValueMapping(source = "Trainer", target = "TRAINER"),
      @ValueMapping(source = "God", target = "GOD")
  })
  public RoleEnum from(Role role);

  @ValueMappings({
      @ValueMapping(source = "NONE", target = "None"),
      @ValueMapping(source = "CLIENT", target = "Client"),
      @ValueMapping(source = "TRAINER", target = "Trainer"),
      @ValueMapping(source = "GOD", target = "God")
  })
  public Role from(RoleEnum roleEnum);

  @Mapping(target = "password", ignore=true)
  public UserDTO from(User user);

  @Mapping(target = "events", ignore = true)
  public User from(UserDTO user);
}
