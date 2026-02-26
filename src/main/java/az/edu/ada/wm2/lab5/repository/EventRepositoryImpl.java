package az.edu.ada.wm2.lab5.repository;

import az.edu.ada.wm2.lab5.model.Event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class EventRepositoryImpl implements EventRepository {
    private final Map<UUID, Event> eventStore = new HashMap<>();

    @Override
    public Event save(Event event) {
        if (event.getId() == null) {
            event.setId(UUID.randomUUID());
        }
        eventStore.put(event.getId(), event);
        return event;
    }

    @Override
    public Optional<Event> findById(UUID id) {
        return Optional.ofNullable(eventStore.get(id));
    }

    @Override
    public List<Event> findAll() {
        return new ArrayList<>(eventStore.values());
    }

    @Override
    public void deleteById(UUID id) {
        eventStore.remove(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return eventStore.containsKey(id);
    }

    @Override
    public List<Event> findByTagsContaining(String tag) {
        List<Event> matchingEvents = new ArrayList<>();
        for (Event event : eventStore.values()) {
            if (event.getTags() != null && event.getTags().contains(tag)) {
                matchingEvents.add(event);
            }
        }
        return matchingEvents;
    }

    @Override
    public List<Event> findAllByEventDateTimeAfter(LocalDateTime dateTime) {
        List<Event> upcomingEvents = new ArrayList<>();
        for (Event event : eventStore.values()) {
            if (event.getEventDateTime() != null && event.getEventDateTime().isAfter(dateTime)) {
                upcomingEvents.add(event);
            }
        }
        return upcomingEvents;
    }

    @Override
    public List<Event> findAllByTicketPriceBetween(BigDecimal minPrice, BigDecimal maxPrice) {
        List<Event> eventsInPriceRange = new ArrayList<>();
        for (Event event : eventStore.values()) {
            if (event.getTicketPrice() != null &&
                event.getTicketPrice().compareTo(minPrice) >= 0 &&
                event.getTicketPrice().compareTo(maxPrice) <= 0) {
                eventsInPriceRange.add(event);
            }
        }
        return eventsInPriceRange;
    }

    @Override
    public List<Event> findAllByEventDateTimeBetween(LocalDateTime start, LocalDateTime end) {
        List<Event> eventsInDateRange = new ArrayList<>();
        for (Event event : eventStore.values()) {
            if (event.getEventDateTime() != null &&
                (event.getEventDateTime().isEqual(start) || event.getEventDateTime().isAfter(start)) &&
                (event.getEventDateTime().isEqual(end) || event.getEventDateTime().isBefore(end))) {
                eventsInDateRange.add(event);
            }
        }
        return eventsInDateRange;
    }
}
