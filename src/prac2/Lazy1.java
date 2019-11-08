package prac2;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;

class Lazy<T> {
    private Optional<Supplier<? extends T>> supplierValue = Optional.empty();
    private Optional<? extends T> evaluatedValue = Optional.empty();
    private final List<Function<? super T, ? extends Lazy<? extends T>>> actions = new ArrayList<>();

    Lazy(T evaluatedValue) {
        this.evaluatedValue = Optional.of(evaluatedValue);
    }

    Lazy(Supplier<T> s) {
        this.supplierValue = Optional.of(s);
    }

    static <T> Lazy<T> of(T v) {
        return new Lazy<>(v);
    }

    static <T> Lazy<T> of(Supplier<T> s) {
        return new Lazy<>(s);
    }

    /**
     * An improved get method which now checks that even though an evaluated value has been supplied, it has not been 'getted' yet.
     */
    public Lazy<T> get() {
        this.evaluatedValue.ifPresentOrElse(consumer -> {
        }, () -> this.supplierValue.ifPresent(consumer -> this.evaluatedValue = Optional.of(consumer.get())));

        this.actions.forEach(action -> this.evaluatedValue.ifPresent(consumer -> this.evaluatedValue = action.apply(consumer).getEvaluatedValue()));
        this.actions.clear();

        return this;
    }

    protected Optional<? extends T> getEvaluatedValue() {
        return this.evaluatedValue;
    }

    public Lazy<T> map(Function<? super T, ? extends T> mapper) {
        return this.flatMap(consumer -> Lazy.of(mapper.apply(consumer)));
    }

    public Lazy<T> flatMap(Function<? super T, ? extends Lazy<? extends T>> mapper) {
        this.evaluatedValue.ifPresent(consumer -> this.actions.add(mapper));
        return this;
    }

    @Override
    public String toString() {
        if (this.actions.isEmpty()) {
            StringBuilder returnValue = new StringBuilder();
            this.evaluatedValue.ifPresentOrElse(consumer -> {
                returnValue.append(consumer);
                if (consumer instanceof String) {
                    returnValue.insert(0, "\"");
                    returnValue.append("\"");
                }
            }, () -> {
                returnValue.append("?");
            });
            return returnValue.toString();
        } else {
            return "?";
        }
    }
}
