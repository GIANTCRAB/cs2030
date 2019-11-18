package cs2030.simulator;

import java.util.Optional;

/**
 * The fundamental of all different type of counters.
 * This provides a set of stable methods which all counters share
 */
interface CheckoutCounter extends Comparable<CheckoutCounter> {
    Optional<Event[]> addCustomerToCounter(double time, Customer customer);

    Optional<Event[]> startServingCustomer(double time);

    CheckoutQueue<?> getCheckoutQueue();

    boolean isServingCustomer();

    boolean isIdle();

    boolean isAvailable();

    boolean canAcceptCustomer();
}
