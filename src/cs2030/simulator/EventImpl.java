package cs2030.simulator;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * The event implementation is the fundamental class that is depended upon by all other classes
 * The huge amount of dependence on this class meant that an Event interface is needed to provide more stability.
 *
 */
class EventImpl implements Event {
    /**
     * The time this event occurs at.
     */
    private final double time;

    /**
     * The action to execute
     */
    private final Supplier<Optional<Event[]>> action;

    EventImpl(double time, Supplier<Optional<Event[]>> action) {
        this.time = time;
        this.action = action;
    }

    @Override
    public double getTime() {
        return this.time;
    }

    /**
     * Running an event can output several other events to be processed
     *
     * @return Possible events to further execute
     */
    @Override
    public Optional<Event[]> execute() {
        return this.action.get();
    }

    /**
     * Sort the events by ascending time order
     *
     * @param otherEvent Other events to compare with
     * @return The sort order
     */
    @Override
    public int compareTo(Event otherEvent) {
        return (int) Math.signum(this.getTime() - otherEvent.getTime());
    }
}
