package dev.fizlrock.suphoto.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.openapitools.model.EventDTO;
import org.openapitools.model.UserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.fizlrock.suphoto.domain.entity.Event;
import dev.fizlrock.suphoto.domain.entity.User;


/**
 * MapperConfig
 */
@Configuration
public class MapperConfiguration {

  @Bean
  public ModelMapper mapper() {
    ModelMapper mm = new ModelMapper();

    TypeMap<EventDTO, Event> eventMapping = mm.createTypeMap(EventDTO.class, Event.class);
    eventMapping.addMapping(EventDTO::getTitle, Event::setTitle);
    eventMapping.addMapping(EventDTO::getLocation, Event::setLocation);

    TypeMap<UserDTO, User> userDTOMapping = mm.createTypeMap(UserDTO.class, User.class);

    userDTOMapping.addMapping(UserDTO::getUsername, User::setUsername);
    userDTOMapping.addMapping(UserDTO::getPatronymic, User::setPartonymic);

    TypeMap<User, UserDTO> DTOUserMapping = mm.createTypeMap(User.class, UserDTO.class);

    DTOUserMapping.addMapping(User::getPartonymic, UserDTO::setPatronymic);

    return mm;
  }
}
