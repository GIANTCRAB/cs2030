package cs2030;

import java.util.Optional;

public class ServerCounter implements HasOneCheckoutHandler {
    private final CheckoutQueue checkoutQueue;
    private final CheckoutHandler checkoutHandler;
    private Optional<Customer> currentlyServing;

    ServerCounter(ServerCheckoutQueue serverCheckoutQueue, CheckoutHandler checkoutHandler) {
        this.checkoutQueue = serverCheckoutQueue;
        this.checkoutHandler = checkoutHandler;
    }

    @Override
    public CheckoutQueue getCheckoutQueue() {
        return this.checkoutQueue;
    }

    @Override
    public CheckoutHandler getCheckoutHandler() {
        return this.checkoutHandler;
    }

    @Override
    public void startServingCustomer() {
        final Customer customer = this.getCheckoutQueue().pollCustomer();
        this.currentlyServing = Optional.of(customer);
    }

    @Override
    public boolean isServingCustomer() {
        return this.currentlyServing.isPresent();
    }

    @Override
    public boolean isIdle() {
        return !this.isServingCustomer() && this.getCheckoutQueue().getCurrentQueueLength() == 0;
    }

    @Override
    public void finishServingCustomer() {
        this.currentlyServing = Optional.empty();
    }
}
