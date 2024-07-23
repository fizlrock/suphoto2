package dev.fizlrock.suphoto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.openapitools.model.UserDTO;
import org.openapitools.model.UserDTO.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import dev.fizlrock.suphoto.domain.entity.User;
import dev.fizlrock.suphoto.domain.entity.User.Role;
import dev.fizlrock.suphoto.mappers.UserMapper;
import dev.fizlrock.suphoto.mappers.UserMapperImpl;

@SpringBootTest(classes = { UserMapperImpl.class })
public class UserMapperTests {

  @Autowired
  UserMapper userMapper;

  @Test
  void mapperNotNull() {
    assertNotNull(userMapper);
  }

  static final String fname = "Владимир";
  static final String lname = "Кутин";
  static final String patro = "Владимирович";

  @Test
  void mapDomainToDTO() {
    Role role = Role.Client;
    RoleEnum dtoRole = RoleEnum.CLIENT;

    var user = User.builder()
        .firstname(fname)
        .lastname(lname)
        .patronymic(patro)
        .role(Role.Client)
        .events(new HashSet<>())
        .build();

    var dto = userMapper.from(user);

    assertEquals(fname, dto.getFirstname());
    assertEquals(lname, dto.getLastname());
    assertEquals(patro, dto.getPatronymic());
    assertEquals(dtoRole, dto.getRole());
  }


}
