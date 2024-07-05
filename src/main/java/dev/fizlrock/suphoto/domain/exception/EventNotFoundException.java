package dev.fizlrock.suphoto.domain.exception;

/**
 * UserNotFoundException
 */
public class EventNotFoundException extends RuntimeException {
  public EventNotFoundException(Number id) {
    super(String.format("Пользователь с id %d не найден", id));
  }
}
