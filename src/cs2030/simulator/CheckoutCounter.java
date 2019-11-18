package cs2030.simulator;

import java.util.Optional;

interface CheckoutCounter extends Comparable<CheckoutCounter> {
    Optional<Event[]> addCustomerToCounter(double time, Customer customer);

    Optional<Event[]> startServingCustomer(double time);

    CheckoutQueue<?> getCheckoutQueue();

    boolean isServingCustomer();

    boolean isIdle();

    boolean isAvailable();

    boolean canAcceptCustomer();
}
