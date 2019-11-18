package cs2030.simulator;

import java.util.Optional;

interface CheckoutCounter {
    Optional<Event[]> addCustomerToCounter(double time, Customer customer);

    Optional<Event[]> startServingCustomer(double time);

    boolean isServingCustomer();

    boolean isIdle();

    boolean isAvailable();

    boolean canAcceptCustomer();

    Optional<Event[]> finishServingCustomer(double time);
}
