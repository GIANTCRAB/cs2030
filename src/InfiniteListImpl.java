import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class InfiniteListImpl<T> implements InfiniteList<T> {
    private final Supplier<Optional<? extends T>> head;
    private final Supplier<InfiniteListImpl<T>> tail;


    private InfiniteListImpl(Supplier<Optional<? extends T>> head, Supplier<InfiniteListImpl<T>> tail) {
        this.head = head;
        this.tail = tail;
    }

    public static <T> InfiniteListImpl<T> generate(Supplier<? extends T> supplier) {
        return new InfiniteListImpl<>(() -> Optional.of(supplier.get()),
                () -> InfiniteListImpl.generate(supplier));
    }

    public static <T> InfiniteListImpl<T> iterate(T seed, Function<? super T, ? extends T> next) {
        return new InfiniteListImpl<>(() -> Optional.of(seed),
                () -> InfiniteListImpl.iterate(next.apply(seed), next));
    }

    public InfiniteListImpl<T> get() {
        final InfiniteListImpl<T> infiniteListCopy = new InfiniteListImpl<>(this.head, this.tail);
        final InfiniteListImpl<T> getResult = infiniteListCopy.tail.get();
        System.out.println(this.head.get().get());
        return new InfiniteListImpl<>(getResult.head, getResult.tail);
    }

    public <R> InfiniteListImpl<R> map(Function<? super T, ? extends R> mapper) {
        return new InfiniteListImpl<>(
                () -> Optional.of(mapper.apply(this.head.get().get())),
                () -> this.tail.get().map(mapper));
    }

    public InfiniteListImpl<T> filter(Predicate<? super T> predicate) {
        return new InfiniteListImpl<>(
                () -> this.head.get().filter(predicate),
                () -> this.tail.get().filter(predicate));
    }
}
