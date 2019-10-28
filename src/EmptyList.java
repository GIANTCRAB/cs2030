import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class EmptyList<T> extends InfiniteListImpl<T> {
    private EmptyList() {
        super(Optional::empty, EmptyList::new);
    }

    @Override
    public <R> InfiniteListImpl<R> map(Function<? super T, ? extends R> mapper) {
        return new EmptyList<>();
    }

    @Override
    public EmptyList<T> filter(Predicate<? super T> predicate) {
        return new EmptyList<>();
    }
}
