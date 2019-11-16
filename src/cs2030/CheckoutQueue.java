package cs2030;

import java.util.ArrayDeque;
import java.util.Queue;

abstract class CheckoutQueue {
    private final Queue<Customer> customerQueue;
    private final int maxQueueCapacity;

    CheckoutQueue(int maxQueueCapacity) {
        this.customerQueue = new ArrayDeque<>();
        this.maxQueueCapacity = maxQueueCapacity;
    }

    public int getCurrentQueueLength() {
        return this.customerQueue.size();
    }

    public int getMaxQueueCapacity() {
        return this.maxQueueCapacity;
    }

    public boolean canJoinCustomerQueue() {
        return this.customerQueue.size() < this.getMaxQueueCapacity();
    }

    public CheckoutQueue joinCustomerQueue(Customer customer) {
        final Queue<Customer> customerQueue = this.customerQueue;

        if (this.canJoinCustomerQueue()) {
            customerQueue.add(customer);
        }

        return this;
    }

    public Customer pollCustomer() {
        return this.customerQueue.poll();
    }
}