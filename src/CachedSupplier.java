import java.util.function.Supplier;

public class CachedSupplier<T> {
    private final Supplier<? extends T> supplier;
    private T cache = null;

    public CachedSupplier(Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        if (this.cache == null) {
            this.cache = this.supplier.get();
        }

        return this.cache;
    }
}
