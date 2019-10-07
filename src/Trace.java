import java.util.*;

public class Trace<T> {
    private final T currentValue;
    private final Optional<List<T>> traceHistory;

    public Trace() {
        this(null, Optional.empty());
    }

    public Trace(T currentValue, Optional<List<T>> traceHistory) {
        this.currentValue = currentValue;
        this.traceHistory = traceHistory;
    }

    @SafeVarargs
    public static <T> Trace<T> of(T currentValue, T... traceValues) {
        final List<T> traceHistory = new ArrayList<>(Arrays.asList(traceValues));
        return new Trace<>(currentValue, Optional.of(traceHistory));
    }

    public T get() {
        return this.currentValue;
    }

    public List<T> history() {
        final Optional<List<T>> traceHistory = this.getTraceHistory();
        if (traceHistory.isPresent()) {
            final List<T> traceList = traceHistory.get();
            traceList.add(this.get());
            return traceList;
        }
        return null;
    }

    public Optional<List<T>> getTraceHistory() {
        return this.traceHistory;
    }
}
