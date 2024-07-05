package dev.fizlrock.suphoto.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.fizlrock.suphoto.domain.entity.User;
import dev.fizlrock.suphoto.domain.entity.User.Role;
import dev.fizlrock.suphoto.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FirstUserApplicatonListener implements ApplicationListener<ContextRefreshedEvent> {

  private PasswordEncoder encoder;
  private UserRepository userRepo;

  @Value("${security.god.default.username}")
  private String username;
  @Value("${security.god.default.password}")
  private String password;

  public FirstUserApplicatonListener(PasswordEncoder encoder, UserRepository userRepo) {
    this.encoder = encoder;
    this.userRepo = userRepo;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    var godInDB = userRepo.findByUsername(username);

    if (godInDB.isEmpty()) {
      var god = User.builder()
          .username(username)
          .password(encoder.encode(password))
          .lastName("Михайлов")
          .firstName("Андрей")
          .partonymic("Игоревич")
          .role(Role.God)
          .build();
      userRepo.save(god);
      log.info("Создана учетная запись администратора");
    } else
      log.info("Учетная запись администратора найдена в БД");

  }

}
