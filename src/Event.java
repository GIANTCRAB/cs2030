import java.util.function.Function;

/**
 * The Event class encapsulates information and methods pertaining to a
 * Simulator event.  This is an abstract class that should be subclassed
 * into a specific event in the simulator.  The {@code simulate} method
 * must be written.
 *
 * @author Woo Huiren
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
public class Event implements Comparable<Event> {
    /**
     * The time this event occurs at.
     */
    private final double time;
    private final Function<SimState, SimState> action;

    /**
     * Creates an event and initializes it.
     *
     * @param time The time of occurrence.
     */
    public Event(double time, Function<SimState, SimState> action) {
        this.time = time;
        this.action = action;
    }

    /**
     * Defines natural ordering of events by their time.
     * Events ordered in ascending order of their timestamps.
     *
     * @param other Another event to compare against.
     * @return 0 if two events occur at same time, a positive number if
     * this event has later than other event, a negative number otherwise.
     */
    public int compareTo(Event other) {
        return (int) Math.signum(this.time - other.time);
    }


    /**
     * The method that simulates this event.
     *
     * @return The updated state after simulating this event.
     */
    public SimState simulate(SimState simState) {
        return this.action.apply(simState);
    }
}
