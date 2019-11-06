import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

class Lazy<T> {
    private transient Supplier<T> supplierValue;
    private volatile Optional<T> evaluatedValue = Optional.empty();

    Lazy(T evaluatedValue) {
        this.evaluatedValue = Optional.of(evaluatedValue);
        this.supplierValue = () -> evaluatedValue;
    }

    Lazy(Supplier<T> s) {
        this.supplierValue = s;
    }

    static <T> Lazy<T> of(T v) {
        return new Lazy<>(v);
    }

    static <T> Lazy<T> of(Supplier<T> s) {
        return new Lazy<>(s);
    }

    public T get() {
        return this.evaluatedValue.orElseGet(() -> {
            T evaluatedValue = this.supplierValue.get();
            try {
                this.evaluatedValue = Optional.of(evaluatedValue);
            } catch (NullPointerException e) {
                return evaluatedValue;
            }
            return evaluatedValue;
        });
    }

    public <R> Lazy<R> map(Function<T, R> mapper) {
        return new Lazy<>(() -> mapper.apply(this.get()));
    }

    public <R> Lazy<R> flatMap(Function<T, Lazy<R>> mapper) {
        return new Lazy<>(() -> mapper.apply(this.get()).get());
    }

    @Override
    public String toString() {
        StringBuilder returnValue = new StringBuilder();
        this.evaluatedValue.ifPresentOrElse(returnValue::append, () -> returnValue.append("?"));
        return returnValue.toString();
    }

    public boolean equals(Lazy<T> otherLazy) {
        return this.evaluatedValue.equals(otherLazy.evaluatedValue)
                && this.supplierValue.equals(otherLazy.supplierValue);
    }
}
