package cs2030.simulator;

/**
 * The specific type of queue used for server counters
 *
 * @param <T>
 */
class ServerCheckoutQueue<T> extends CheckoutQueue<T> {
    ServerCheckoutQueue(int maxQueueCapacity) {
        super(maxQueueCapacity);
    }
}
