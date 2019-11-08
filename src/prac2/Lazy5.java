package prac2;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

class Lazy<T extends Comparable<T>> {
    private transient Supplier<T> supplierValue;
    private volatile Optional<T> evaluatedValue = Optional.empty();

    Lazy(T evaluatedValue) {
        this.evaluatedValue = Optional.of(evaluatedValue);
        this.supplierValue = () -> evaluatedValue;
    }

    Lazy(Supplier<T> s) {
        this.supplierValue = s;
    }

    static <T extends Comparable<T>> Lazy<T> of(T v) {
        return new Lazy<>(v);
    }

    static <T extends Comparable<T>> Lazy<T> of(Supplier<T> s) {
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

    public <R extends Comparable<R>> Lazy<R> map(Function<T, R> mapper) {
        return new Lazy<R>(() -> mapper.apply(this.get()));
    }

    public <R extends Comparable<R>> Lazy<R> flatMap(Function<T, Lazy<R>> mapper) {
        return new Lazy<R>(() -> mapper.apply(this.get()).get());
    }

    public <U extends Comparable<U>, B extends Comparable<B>> Lazy<B> combine(Lazy<U> identity,
                                                                              BiFunction<T, U, B> accumulator) {
        return new Lazy<B>(() -> accumulator.apply(this.get(), identity.get()));
    }

    public Lazy<Boolean> test(Predicate<T> predicate) {
        return new Lazy<Boolean>(() -> predicate.test(this.get()));
    }

    public Lazy<Integer> compareTo(Lazy<T> otherLazy) {
        return new Lazy<Integer>(() -> this.get().compareTo(otherLazy.get()));
    }

    @Override
    public String toString() {
        StringBuilder returnValue = new StringBuilder();
        this.evaluatedValue.ifPresentOrElse(returnValue::append, () -> returnValue.append("?"));
        return returnValue.toString();
    }

    public boolean equals(Lazy<T> otherLazy) {
        return this.get().equals(otherLazy.get());
    }

    @Override
    public boolean equals(Object otherObject) {
        return false;
    }
}
