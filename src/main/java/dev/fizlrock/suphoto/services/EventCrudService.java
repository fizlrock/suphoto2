package dev.fizlrock.suphoto.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openapitools.model.EventDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import dev.fizlrock.suphoto.domain.entity.Event;
import dev.fizlrock.suphoto.domain.exception.EventNotFoundException;
import dev.fizlrock.suphoto.mappers.EventMapper;
import dev.fizlrock.suphoto.repositories.EventRepository;

/**
 * EventCrudService
 */
@Service
public class EventCrudService {

  public EventCrudService(EventRepository eventRepo, EventMapper mapper) {
    this.eventRepo = eventRepo;
    this.eventMapper = mapper;
  }

  EventRepository eventRepo;
  EventMapper eventMapper;

  public EventDTO saveEvent(EventDTO eventDTO) {
    Event event = eventMapper.from(eventDTO);
    Event savedEvent = eventRepo.save(event);
    EventDTO savedEventDTO = eventMapper.from(savedEvent);
    return savedEventDTO;
  }

  public EventDTO createEvent(EventDTO eventDTO) {
    eventDTO.setId(null);
    return saveEvent(eventDTO);
  }

  public EventDTO findEventById(Long id) {
    Optional<Event> event = eventRepo.findById(id);
    if (event.isPresent())
      return eventMapper.from(event.get());
    else
      throw new EventNotFoundException(id);
  }

  public void deleteEventById(Long id) {
    if (!eventRepo.existsById(id))
      throw new EventNotFoundException(id);
    eventRepo.deleteById(id);
  }

  public List<EventDTO> findAllEvents(PageRequest pageRequest) {

    List<EventDTO> users = eventRepo.findAll(pageRequest).stream()
        .map(eventMapper::from)
        .collect(Collectors.toList());

    return users;
  }
}
