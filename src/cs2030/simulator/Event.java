package cs2030.simulator;

import java.util.Optional;

public interface Event extends Comparable<Event> {
    double getTime();

    default Optional<Event[]> execute() {
        return Optional.empty();
    }
}
