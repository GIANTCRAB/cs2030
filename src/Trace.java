import java.util.*;

public class Trace<T> {
    private final T currentValue;
    private final List<T> traceHistory;

    public Trace() {
        this(null, null);
    }

    public Trace(T currentValue, List<T> traceHistory) {
        this.currentValue = currentValue;
        this.traceHistory = traceHistory;
    }

    @SafeVarargs
    public static <T> Trace<T> of(T currentValue, T... traceValues) {
        final List<T> traceHistory = new ArrayList<>(Arrays.asList(traceValues));
        return new Trace<>(currentValue, traceHistory);
    }

    public T get() {
        return this.currentValue;
    }

    public List<T> history() {
        return this.traceHistory;
    }

    public Trace<T> back(int steps) {
        final List<T> history = this.history();
        final int newSize = history.size() - steps - 1;
        final List<T> newHistory = history.subList(0, newSize);
        final T newCurrentValue = history.get(newSize);

        return new Trace<>(newCurrentValue, newHistory);
    }

    public boolean equals(Trace<T> trace) {
        return this.get().equals(trace.get()) && this.history().equals(trace.history());
    }
}
