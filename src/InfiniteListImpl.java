import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.*;

public class InfiniteListImpl<T> implements InfiniteList<T> {
    private final Supplier<Optional<? extends T>> head;
    private final Supplier<InfiniteListImpl<T>> tail;

    private InfiniteListImpl() {
        this.head = Optional::empty;
        this.tail = EmptyList::new;
    }

    protected InfiniteListImpl(Supplier<Optional<? extends T>> head, Supplier<InfiniteListImpl<T>> tail) {
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
        if (this.head.get().isPresent()) {
            System.out.println(this.head.get().get());
        }
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

    public boolean isEmptyList() {
        return this instanceof EmptyList;
    }

    public InfiniteListImpl<T> limit(long limit) {
        final InfiniteListImpl<T> infiniteListCopy = new InfiniteListImpl<>(this.head, this.tail);

        return new InfiniteListImpl<>(() -> {
            if (limit <= 0) {
                return Optional.empty();
            }

            return infiniteListCopy.head.get();
        }, () -> {
            if (limit <= 0 || (limit == 1 && infiniteListCopy.head.get().isPresent())) {
                return new EmptyList<>();
            }

            final InfiniteListImpl<T> currentTail = infiniteListCopy.tail.get();

            if (currentTail.isEmptyList()) {
                return currentTail;
            } else {
                return currentTail.limit(limit - (infiniteListCopy.head.get().isPresent() ? 1 : 0));
            }
        });
    }

    public void forEach(Consumer<? super T> action) {
        InfiniteListImpl<T> infiniteListCopy = new InfiniteListImpl<>(this.head, this.tail);

        while (!infiniteListCopy.isEmptyList()) {
            infiniteListCopy.head.get().ifPresent(action);
            infiniteListCopy = infiniteListCopy.tail.get();
        }
    }

    public Object[] toArray() {
        InfiniteListImpl<T> infiniteListCopy = new InfiniteListImpl<>(this.head, this.tail);
        final List<T> infiniteListArray = new ArrayList<>();

        while (!infiniteListCopy.isEmptyList()) {
            if (infiniteListCopy.head.get().isPresent()) {
                infiniteListArray.add(infiniteListCopy.head.get().get());
            }
            infiniteListCopy = infiniteListCopy.tail.get();
        }

        return infiniteListArray.toArray();
    }

    public long count() {
        return this.toArray().length;
    }

    private class EmptyList<T> extends InfiniteListImpl<T> {
        @Override
        public <R> InfiniteListImpl<R> map(Function<? super T, ? extends R> mapper) {
            return new EmptyList<>();
        }

        @Override
        public EmptyList<T> filter(Predicate<? super T> predicate) {
            return new EmptyList<>();
        }
    }
}
