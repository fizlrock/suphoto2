package dev.fizlrock.suphoto.mappers;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.openapitools.model.EventDTO;

import dev.fizlrock.suphoto.domain.entity.Event;

/**
 * UserMapper
 */
@Mapper(componentModel = "spring")
public interface EventMapper {
  EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

  default OffsetDateTime from(LocalDateTime datetime) {
    return datetime.atOffset(ZoneOffset.UTC);
  }

  default LocalDateTime from(OffsetDateTime datetime) {
    return datetime.toLocalDateTime();
  }

  public Event from(EventDTO dto);
  public EventDTO from(Event dto);

}
