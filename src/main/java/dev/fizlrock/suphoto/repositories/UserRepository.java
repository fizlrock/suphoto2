package dev.fizlrock.suphoto.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import dev.fizlrock.suphoto.domain.entity.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {

  public Optional<User> findByUsername(String username);

}
