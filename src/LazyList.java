import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.IntStream;

public class LazyList<T extends Comparable<T>> {
    private final List<T> list = new ArrayList<>();
    private Lazy<T> lazySeed;
    private final UnaryOperator<T> method;
    private final int maxLimit;

    private LazyList(Lazy<T> lazySeed, UnaryOperator<T> method, int maxLimit) {
        this.lazySeed = lazySeed;
        this.method = method;
        this.maxLimit = maxLimit;
        this.list.add(lazySeed.get());
    }

    static <T extends Comparable<T>> LazyList<T> generate(int n, T seed, UnaryOperator<T> f) {
        return new LazyList<>(Lazy.of(seed), f, n);
    }

    public T get(int i) {
        if (this.list.size() < i + 1) {
            final int loopCount = i + 1 - this.list.size();
            IntStream.range(0, loopCount).forEach(consumer -> {
                this.lazySeed = this.lazySeed.map(this.method);
                this.list.add(this.lazySeed.get());
            });
        }
        return this.list.get(i);
    }

    public int indexOf(T v) {
        final int indexOfQuery = this.list.indexOf(v);
        if (indexOfQuery == -1) {
            return IntStream.range(this.list.size(), maxLimit)
                    .filter(userIndex -> {
                        this.lazySeed = this.lazySeed.map(this.method);
                        T lazySeedResult = this.lazySeed.get();
                        this.list.add(lazySeedResult);
                        return lazySeedResult.equals(v);
                    })
                    .findFirst()
                    .orElse(-1);
        }

        return indexOfQuery;
    }
}
