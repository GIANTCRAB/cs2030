package cs2030;

import java.util.Optional;

public interface HasRestState {
    RestStates getRestState();

    Optional<Event[]> takeRest(double doneTime);

    Optional<Event[]> stopRest();
}
