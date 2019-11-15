package cs2030;

import java.util.ArrayDeque;
import java.util.Queue;

abstract class ShopCheckoutQueue {
    private final Queue<Customer> customerQueue;
    private final int maxQueueCapacity;

    ShopCheckoutQueue(int maxQueueCapacity) {
        this.customerQueue = new ArrayDeque<>();
        this.maxQueueCapacity = maxQueueCapacity;
    }

    public int getMaxQueueCapacity() {
        return this.maxQueueCapacity;
    }

    public boolean canJoinCustomerQueue() {
        return this.customerQueue.size() < this.getMaxQueueCapacity();
    }

    public ShopCheckoutQueue joinCustomerQueue(Customer customer) {
        final Queue<Customer> customerQueue = this.getCustomerQueue();

        if (this.canJoinCustomerQueue()) {
            customerQueue.add(customer);
        }

        return this;
    }

    public Customer pollCustomer() {
        return this.getCustomerQueue().poll();
    }

    private Queue<Customer> getCustomerQueue() {
        return this.customerQueue;
    }
}
