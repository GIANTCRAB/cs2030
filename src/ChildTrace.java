import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChildTrace<T> extends Trace<T> {
    public ChildTrace(T currentValue, List<T> traceHistory) {
        super(currentValue, traceHistory);
    }

    @SafeVarargs
    public static <T> ChildTrace<T> of(T currentValue, T... traceValues) {
        final List<T> traceHistory = new ArrayList<>(Arrays.asList(traceValues));
        return new ChildTrace<>(currentValue, traceHistory);
    }
}
