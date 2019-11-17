package cs2030;

import java.util.Optional;
import java.util.function.Supplier;

public class EventImpl implements Event {
    /**
     * The time this event occurs at.
     */
    private final double time;

    /**
     * The action to execute
     */
    private final Supplier<Optional<Event>> action;

    public EventImpl(double time, Supplier<Optional<Event>> action) {
        this.time = time;
        this.action = action;
    }

    @Override
    public double getTime() {
        return this.time;
    }

    @Override
    public Optional<Event> execute() {
        return this.action.get();
    }

    @Override
    public int compareTo(Event otherEvent) {
        return (int) Math.signum(this.getTime() - otherEvent.getTime());
    }
}
