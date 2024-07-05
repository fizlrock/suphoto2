package dev.fizlrock.suphoto.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.fizlrock.suphoto.domain.entity.Event;
import dev.fizlrock.suphoto.domain.entity.User;
import dev.fizlrock.suphoto.domain.entity.User.Role;
import dev.fizlrock.suphoto.domain.exception.EventNotFoundException;
import dev.fizlrock.suphoto.domain.exception.UserNotFoundException;
import dev.fizlrock.suphoto.repositories.EventRepository;
import dev.fizlrock.suphoto.repositories.UserRepository;

/**
 * Сервис для работы с ведущими мероприятия. <br>
 * Функционал:
 * <ol>
 * <li>Записать инструктора на мероприятие
 * <li>Отменить записть инструктора
 * <li>Получить список мероприятий
 * <li>Получить список инструкторов
 * </ol>
 * TrainerService
 */
@Service
public class TrainerService {

  public TrainerService(UserRepository userRepo, EventRepository eventRepo) {
    this.eventRepo = eventRepo;
    this.userRepo = userRepo;
  }

  private UserRepository userRepo;
  private EventRepository eventRepo;

  /**
   * Добавляет пользователя в список ведущих мероприятия
   * <br>
   * Возможные ошибки:
   * <ol>
   * <li>Мероприятие не найдено
   * <li>Пользователь не найден
   * <li>Пользователь не является инструктором
   * <li>Инструктор уже записан на мероприятие
   * </ol>
   * 
   * @param trainerId
   * @param eventId
   */
  public void addEventToTrainer(Long trainerId, Long eventId) {

    Event event = eventRepo
        .findById(eventId)
        .orElseThrow(() -> new EventNotFoundException(eventId));

    User user = userRepo
        .findById(trainerId)
        .orElseThrow(() -> new UserNotFoundException(trainerId));

    if (user.getRole() != Role.Trainer)
      throw new IllegalStateException("Пользователь не является инструктором");

    if (event.getStaff().contains(user))
      throw new IllegalStateException("Инструктор уже записан на мероприятие");

    user.getEvents().add(event);
    userRepo.save(user);
  }

  /**
   * Исключает инструктора из списка ведущих <br>
   * 
   * Возможные ошибки:
   * <ol>
   * <li>Мероприятие не найдено
   * <li>Пользователь не найден
   * <li>Инструктор не записан на мероприятие
   * </ol>
   * 
   * @param trainerId
   * @param eventId
   */
  public void kickTrainerFromEvent(Long trainerId, Long eventId) {

    // С этим копипастом нужно что-то сделать...

    Event event = eventRepo
        .findById(eventId)
        .orElseThrow(() -> new EventNotFoundException(eventId));

    User user = userRepo
        .findById(trainerId)
        .orElseThrow(() -> new UserNotFoundException(trainerId));

    if (!event.getStaff().contains(user))
      throw new IllegalStateException("Инструктор не записан на мероприятие");

    user.getEvents().remove(event);
    userRepo.save(user);

  }

  /**
   * Получить все идентификаторы инструкторов, ведущих данное мероприятие
   * 
   * @return Список индентификаторов инструкторов
   */
  public List<Long> getAllTranersOfEvent(Long eventID) {
    Event event = eventRepo
        .findById(eventID)
        .orElseThrow(() -> new EventNotFoundException(eventID));

    List<Long> idsOfStaff = event.getStaff().stream()
        .map(User::getId)
        .collect(Collectors.toList());

    return idsOfStaff;
  }

  /**
   * Получить все идентификаторы инструкторов, ведущих данное мероприятие
   * 
   * @param trainerID - идентификатор инструктора
   * @return Список идентификаторов мепроприятий
   */
  public List<Long> getAllEventsOfTrainer(Long trainerID) {

    User user = userRepo
        .findById(trainerID)
        .orElseThrow(() -> new UserNotFoundException(trainerID));

    List<Long> idsOfEvents = user.getEvents().stream()
        .map(Event::getId)
        .collect(Collectors.toList());

    return idsOfEvents;
  }

}
