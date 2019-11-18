package cs2030.simulator;

import java.util.Optional;

interface HasRestState {
    RestStates getRestState();

    Optional<Event[]> takeRest(double doneTime);

    Optional<Event[]> stopRest();
}
