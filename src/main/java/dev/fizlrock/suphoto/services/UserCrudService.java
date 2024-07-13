package dev.fizlrock.suphoto.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.openapitools.model.NewUserDTO;
import org.openapitools.model.UserDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import dev.fizlrock.suphoto.domain.entity.User;
import dev.fizlrock.suphoto.domain.exception.UserNotFoundException;
import dev.fizlrock.suphoto.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserCrudService {

  public UserCrudService(UserRepository userRepo, ModelMapper mapper) {
    this.userRepo = userRepo;
    this.mapper = mapper;
  }

  private UserRepository userRepo;
  private ModelMapper mapper;

  public UserDTO saveUser(UserDTO rawUser) {

    User user = mapper.map(rawUser, User.class);
    user.setPassword("default password");
    User savedUser = userRepo.save(user);
    UserDTO savedUserDTO = mapper.map(savedUser, UserDTO.class);
    return savedUserDTO;
  }

  public UserDTO createUser(NewUserDTO rawUser) {

    log.warn("Client new user request: {}", rawUser);

    User newUser = mapper.map(rawUser, User.class);
    log.warn("After mapping: {}", newUser);
    return new UserDTO();
  }

  public UserDTO findUserById(Long id) throws UserNotFoundException {
    Optional<User> user = userRepo.findById(id);

    if (user.isPresent())
      return mapper.map(user, UserDTO.class);
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
        .map(x -> mapper.map(x, UserDTO.class))
        .collect(Collectors.toList());

    return users;
  }

}
