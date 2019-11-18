package cs2030.simulator;

class SelfCheckoutQueue<T> extends CheckoutQueue<T> {
    SelfCheckoutQueue(int maxQueueCapacity) {
        super(maxQueueCapacity);
    }

    public T peekCustomer() {
        return this.customerQueue.peek();
    }
}
