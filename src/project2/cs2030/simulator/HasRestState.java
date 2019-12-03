package project2.cs2030.simulator;

import java.util.Optional;

interface HasRestState {
    RestStates getRestState();

    Event takeRest(double doneTime);

    Optional<Event[]> stopRest();
}
