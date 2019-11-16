package cs2030;

import java.util.Optional;

public class ServerCounter implements HasOneCheckoutHandler {
    private final CheckoutQueue checkoutQueue;
    private final Server server;
    private Optional<Customer> currentlyServing;

    ServerCounter(ServerCheckoutQueue serverCheckoutQueue, Server server) {
        this.checkoutQueue = serverCheckoutQueue;
        this.server = server;
    }

    @Override
    public CheckoutHandler getCheckoutHandler() {
        return this.server;
    }

    @Override
    public void addCustomerToCounter(Customer customer) {
        if (this.canAcceptCustomer()) {
            this.checkoutQueue.joinCustomerQueue(customer);
        }
    }

    @Override
    public void startServingCustomer() {
        final Customer customer = this.checkoutQueue.pollCustomer();
        this.currentlyServing = Optional.of(customer);
    }

    @Override
    public boolean isServingCustomer() {
        return this.currentlyServing.isPresent();
    }

    @Override
    public boolean isIdle() {
        return !this.isServingCustomer() && this.checkoutQueue.getCurrentQueueLength() == 0;
    }

    @Override
    public boolean canAcceptCustomer() {
        return this.checkoutQueue.canJoinCustomerQueue();
    }

    @Override
    public void finishServingCustomer() {
        this.currentlyServing = Optional.empty();
    }
}
