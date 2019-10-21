import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public class InfiniteListImpl<T> implements InfiniteList<T> {
    private final Supplier<? extends T> head;
    private final Supplier<InfiniteListImpl<T>> tail;


    private InfiniteListImpl(Supplier<? extends T> head, Supplier<InfiniteListImpl<T>> tail) {
        this.head = head;
        this.tail = tail;
    }

    public static <T> InfiniteListImpl<T> generate(Supplier<? extends T> supplier) {
        Objects.requireNonNull(supplier);
        return new InfiniteListImpl<>(supplier,
                () -> InfiniteListImpl.generate(supplier));
    }

    public static <T> InfiniteListImpl<T> iterate(T seed, Function<? super T, ? extends T> next) {
        return new InfiniteListImpl<>(() -> seed,
                () -> InfiniteListImpl.iterate(next.apply(seed), next));
    }

    public InfiniteListImpl<T> get() {
        final InfiniteListImpl<T> infiniteListCopy = new InfiniteListImpl<>(this.head, this.tail);
        final InfiniteListImpl<T> getResult = infiniteListCopy.tail.get();
        System.out.println(this.head.get());
        return new InfiniteListImpl<>(getResult.head, getResult.tail);
    }
}
