import java.util.*;
import java.util.function.Function;

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

    /**
     * Returns the list of trace history + current value
     *
     * @return List<T> history
     */
    public List<T> history() {
        final List<T> wholeHistory = this.getTraceHistory();
        wholeHistory.add(this.get());
        return wholeHistory;
    }

    public List<T> getTraceHistory() {
        return this.traceHistory;
    }

    public Trace<T> back(int steps) {
        final List<T> history = this.getTraceHistory();
        if (steps > history.size()) {
            steps = history.size();
        }
        final int newSize = history.size() - steps;
        final List<T> newHistory = history.subList(0, newSize);
        final T newCurrentValue = history.get(newSize);

        return new Trace<>(newCurrentValue, newHistory);
    }

    public boolean equals(Trace<T> trace) {
        return this.get().equals(trace.get()) && this.getTraceHistory().equals(trace.getTraceHistory());
    }

    public Trace<T> map(Function<? super T, ? extends T> mapper) {
        Objects.requireNonNull(mapper);
        final List<T> newTraceHistory = this.getTraceHistory();
        newTraceHistory.add(this.get());
        final T newCurrentValue = mapper.apply(this.get());
        return new Trace<>(newCurrentValue, newTraceHistory);
    }

    public Trace<T> flatMap(Function<? super T, Trace<T>> mapper) {
        Objects.requireNonNull(mapper);
        final List<T> newTraceHistory = this.getTraceHistory();
        final Trace<T> newTrace = mapper.apply(this.get());
        newTraceHistory.addAll(newTrace.getTraceHistory());
        return new Trace<>(newTrace.get(), newTraceHistory);
    }
}
