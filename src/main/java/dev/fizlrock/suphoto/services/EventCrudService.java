package dev.fizlrock.suphoto.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.openapitools.model.EventDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import dev.fizlrock.suphoto.domain.entity.Event;
import dev.fizlrock.suphoto.domain.exception.EventNotFoundException;
import dev.fizlrock.suphoto.repositories.EventRepository;

/**
 * EventCrudService
 */
@Service
public class EventCrudService {

  public EventCrudService(EventRepository eventRepo, ModelMapper mapper) {
    this.eventRepo = eventRepo;
    this.mapper = mapper;
  }

  EventRepository eventRepo;
  ModelMapper mapper;

  public EventDTO saveEvent(EventDTO eventDTO) {
    Event event = mapper.map(eventDTO, Event.class);
    Event savedEvent = eventRepo.save(event);
    EventDTO savedEventDTO = mapper.map(savedEvent, EventDTO.class);
    return savedEventDTO;
  }

  public EventDTO createEvent(EventDTO eventDTO) {
    eventDTO.setId(null);
    return saveEvent(eventDTO);
  }

  public EventDTO findEventById(Long id) {
    Optional<Event> event = eventRepo.findById(id);
    if (event.isPresent())
      return mapper.map(event, EventDTO.class);
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
        .map(x -> mapper.map(x, EventDTO.class))
        .collect(Collectors.toList());

    return users;
  }
}
