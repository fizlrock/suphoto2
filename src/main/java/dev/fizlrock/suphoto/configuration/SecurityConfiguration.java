package dev.fizlrock.suphoto.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.fizlrock.suphoto.repositories.UserRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepo) {

    var users = new UserDetailsService() {
      @Override
      public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return User.withUsername(username)
            .password(user.getPassword())
            .roles(user.getRole().toString())
            .build();
      }
    };

    return users;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
