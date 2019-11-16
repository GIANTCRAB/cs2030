package cs2030;

public class ServerCounter implements HasOneCheckoutHandler {
    private final CheckoutQueue checkoutQueue;
    private final CheckoutHandler checkoutHandler;

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
}
