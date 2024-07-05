package dev.fizlrock.suphoto.domain.exception;

/**
 * UserNotFoundException
 */
public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(Number id) {
    super(String.format("Пользователь с id %d не найден", id));
  }
}
