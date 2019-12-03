package project2.cs2030.simulator;

import java.util.Optional;

interface Event extends Comparable<Event> {
    double getTime();

    default Optional<Event[]> execute() {
        return Optional.empty();
    }
}
