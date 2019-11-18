package cs2030.simulator;

import java.util.ArrayDeque;
import java.util.Queue;

abstract class CheckoutQueue<T> {
    protected final Queue<T> customerQueue;
    private final int maxQueueCapacity;

    CheckoutQueue(int maxQueueCapacity) {
        this.customerQueue = new ArrayDeque<>();
        this.maxQueueCapacity = maxQueueCapacity;
    }

    public Integer getCurrentQueueLength() {
        return this.customerQueue.size();
    }

    public int getMaxQueueCapacity() {
        return this.maxQueueCapacity;
    }

    public boolean canJoinCustomerQueue() {
        return this.customerQueue.size() < this.getMaxQueueCapacity();
    }

    public CheckoutQueue joinCustomerQueue(T customer) {
        if (this.canJoinCustomerQueue()) {
            this.customerQueue.add(customer);
        }

        return this;
    }

    public T pollCustomer() {
        return this.customerQueue.poll();
    }
}
