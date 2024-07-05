package dev.fizlrock.suphoto.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import dev.fizlrock.suphoto.domain.entity.Event;



/**
 * EventRepository
 */
public interface EventRepository extends PagingAndSortingRepository<Event, Long> {

}
