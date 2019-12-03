package project2.cs2030.simulator;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * The base abstraction of all queues used by checkout counters.
 * The goal of this class is to reduce code duplication of SelfCheckoutQueue and ServerCheckoutQueue as they have many common ideas
 *
 * @param <T>
 */
abstract class CheckoutQueue<T> {
    private final Queue<T> customerQueue;
    private final int maxQueueCapacity;

    CheckoutQueue(int maxQueueCapacity) {
        this.customerQueue = new ArrayDeque<>();
        this.maxQueueCapacity = maxQueueCapacity;
    }

    Integer getCurrentQueueLength() {
        return this.customerQueue.size();
    }

    private int getMaxQueueCapacity() {
        return this.maxQueueCapacity;
    }

    boolean canJoinCustomerQueue() {
        return this.customerQueue.size() < this.getMaxQueueCapacity();
    }

    CheckoutQueue joinCustomerQueue(T customer) {
        if (this.canJoinCustomerQueue()) {
            this.customerQueue.add(customer);
        }

        return this;
    }

    T pollCustomer() {
        return this.customerQueue.poll();
    }
}
