package cs2030;

import java.util.Arrays;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Stream;

public class EventStreamProvider {
    /**
     * The priority queue of events.
     */
    private final Queue<Event> events = new PriorityQueue<>();

    public EventStreamProvider() {
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public void addEvents(Event[] events) {
        this.events.addAll(Arrays.asList(events));
    }

    public void processEvents() {
        Stream.generate(this.events::poll)
                .takeWhile(Objects::nonNull)
                .forEach(event -> event.execute().ifPresent(this::addEvents));
    }
}
