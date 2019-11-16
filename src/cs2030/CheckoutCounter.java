package cs2030;

public interface CheckoutCounter {
    CheckoutQueue getCheckoutQueue();

    void startServingCustomer();

    boolean isServingCustomer();

    boolean isIdle();

    void finishServingCustomer();
}
