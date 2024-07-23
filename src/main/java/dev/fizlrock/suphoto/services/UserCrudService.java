package dev.fizlrock.suphoto.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openapitools.model.UserDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.fizlrock.suphoto.domain.entity.User;
import dev.fizlrock.suphoto.domain.exception.UserNotFoundException;
import dev.fizlrock.suphoto.mappers.UserMapper;
import dev.fizlrock.suphoto.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserCrudService {

  private PasswordEncoder passEncoder;
  private UserRepository userRepo;
  private UserMapper userMapper;

  public UserCrudService(UserRepository userRepo, UserMapper mapper, PasswordEncoder passEncoder) {
    this.userRepo = userRepo;
    this.userMapper = mapper;
    this.passEncoder = passEncoder;
  }

  public UserDTO saveNewUser(UserDTO rawUser) {
    User user = userMapper.from(rawUser);
    user.setId(null);
    user.setPassword(passEncoder.encode(user.getPassword()));
    User savedUser = userRepo.save(user);
    UserDTO savedUserDTO = userMapper.from(savedUser);
    return savedUserDTO;
  }

  public UserDTO updateExistingUser(Long userId, UserDTO userDTO) {
    User user = userMapper.from(userDTO);
    user.setId(userId);
    userRepo.save(user);
    return userMapper.from(user);
  }

  public UserDTO findUserById(Long id) throws UserNotFoundException {
    Optional<User> user = userRepo.findById(id);

    if (user.isPresent())
      return userMapper.from(user.get());
    else
      throw new UserNotFoundException(id);
  }

  public void deleteUserById(Long id) throws UserNotFoundException {
    if (!userRepo.existsById(id))
      throw new UserNotFoundException(id);
    userRepo.deleteById(id);
  }

  public List<UserDTO> findAllUsers(PageRequest pageRequest) {

    List<UserDTO> users = userRepo.findAll(pageRequest).stream()
        .map(userMapper::from)
        .collect(Collectors.toList());

    return users;
  }

}
