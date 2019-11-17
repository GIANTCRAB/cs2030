package cs2030;

import java.util.Optional;

interface Event extends Comparable<Event> {
    double getTime();

    default Optional<Event> execute() {
        return Optional.empty();
    }
}
