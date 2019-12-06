package peerlearning;

import java.util.function.IntSupplier;
import java.util.stream.IntStream;

public class FibStream {
    public static IntStream fibsLessThan(int fibMax) {
        return IntStream.generate(new FibonacciSupplier())
                .takeWhile(fibNumber -> fibNumber < fibMax);
    }

    public static IntStream fibsInBetween(int min, int max) {
        return FibStream.fibsLessThan(max).filter(fibNumber -> fibNumber > min);
    }

    private static class FibonacciSupplier implements IntSupplier {
        int current = 0;
        int previous = 0;

        @Override
        public int getAsInt() {
            final int result = current;
            if (current == 0) {
                current = 1;
            } else {
                current = previous + current;
            }
            previous = result;
            return result;
        }
    }
}
