// package cs2030.mystream;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.Optional;

public interface InfiniteList<T> {

    static <T> InfiniteList<T> generate(Supplier<? extends T> s) {
        return InfiniteListImpl.generate(s);
    }

    static <T> InfiniteList<T> iterate(T seed, Function<? super T, ? extends T> next) {
        return InfiniteListImpl.iterate(seed, next);
    }

    <R> InfiniteList<R> map(Function<? super T, ? extends R> mapper);

    InfiniteList<T> filter(Predicate<? super T> predicate);

    void forEach(Consumer<? super T> action);

    Object[] toArray();

    InfiniteList<T> limit(long n);

    long count();

    //Optional<T> reduce(BinaryOperator<T> accumulator);

    //<U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator);

    //InfiniteList<T> takeWhile(Predicate<? super T> predicate);

    boolean isEmptyList();
}
