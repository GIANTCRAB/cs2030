package cs2030;

public interface CheckoutCounter {
    void addCustomerToCounter(Customer customer);

    void startServingCustomer();

    boolean isServingCustomer();

    boolean isIdle();

    boolean canAcceptCustomer();

    void finishServingCustomer();
}
