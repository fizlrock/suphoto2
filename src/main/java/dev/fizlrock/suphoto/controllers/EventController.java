package dev.fizlrock.suphoto.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.openapitools.api.EventsApi;
import org.openapitools.model.EventDTO;
import org.openapitools.model.ID;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import dev.fizlrock.suphoto.services.EventCrudService;
import dev.fizlrock.suphoto.services.TrainerService;

@RestController("/events")
public class EventController implements EventsApi {

  public EventController(EventCrudService eventService, TrainerService trainerService) {
    this.eventService = eventService;
    this.trainerService = trainerService;
  }

  EventCrudService eventService;
  TrainerService trainerService;

  // Base CRUD

  @Override
  public ResponseEntity<List<EventDTO>> getAllEvents(Integer pageNumber,
      Integer pageSize) {
    PageRequest pr = PageRequest.of(pageNumber, pageSize);
    return ResponseEntity.ok(eventService.findAllEvents(pr));
  }

  @Override
  public ResponseEntity<EventDTO> createEvent(EventDTO eventDTO) {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(eventService.saveEvent(eventDTO));
  }

  @Override
  public ResponseEntity<Void> deleteEventById(Long eventID) {
    eventService.findEventById(eventID);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Override
  public ResponseEntity<EventDTO> findEventById(Long eventID) {
    return ResponseEntity.ok(eventService.findEventById(eventID));
  }

  @Override
  public ResponseEntity<EventDTO> updateEvent(Long eventID, EventDTO eventDTO) {
    return ResponseEntity.ok(
        eventService.saveEvent(eventDTO));
  }

  // // Logic

  @Override
  public ResponseEntity<Void> addUserToEvent(Long eventID, ID userID) {
    trainerService.addEventToTrainer(userID.getId(), eventID);

    return ResponseEntity.ok(null);
  }

  @Override
  public ResponseEntity<List<ID>> getAllUsersOfEvent(Long eventID) {

    List<ID> ids = trainerService.getAllTranersOfEvent(eventID).stream()
        .map(x -> {
          ID id = new ID();
          id.setId(x);
          return id;
        })
        .collect(Collectors.toList());

    return ResponseEntity.ok(ids);
  }

  @Override
  public ResponseEntity<Void> removeUserFromEvent(Long eventID, ID trainerID) {
    trainerService.kickTrainerFromEvent(trainerID.getId(), eventID);
    return ResponseEntity.ok(null);
  }

}
