package project2.cs2030.simulator;

import java.util.Arrays;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Stream;

/**
 * The main system driving and processing all the events.
 * The event stream provider is a stable class with minimal code.
 * It does not handle any logic and acts as a dumb pipe that executes whatever events are fed to it.
 * It centralizes all events that occurs throughout the entire checkout system.
 *
 * @author woohuiren
 */
class EventStreamProvider {
    /**
     * The priority queue of events.
     */
    private final Queue<Event> events = new PriorityQueue<>();

    void addEvent(Event event) {
        this.events.add(event);
    }

    void addEvents(Event[] events) {
        this.events.addAll(Arrays.asList(events));
    }

    void processEvents() {
        Stream.generate(this.events::poll)
                .takeWhile(Objects::nonNull)
                .forEach(event -> event.execute().ifPresent(this::addEvents));
    }
}
