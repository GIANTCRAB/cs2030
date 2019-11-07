import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class LazyList<T> {
    List<T> list;
    private LazyList(List<T> list) {
        this.list = list;
    }

    static <T> LazyList<T> generate(int n, T seed, UnaryOperator<T> f) {
        return new LazyList<T>(
                Stream.iterate(seed, x -> f.apply(x))
                        .limit(n)
                        .collect(Collectors.toList())
        );
    }

    public T get(int i) {
        return this.list.get(i);
    }

    public int indexOf(T v) {
        return this.list.indexOf(v);
    }
}
