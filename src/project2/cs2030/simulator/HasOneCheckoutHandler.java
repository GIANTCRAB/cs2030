package project2.cs2030.simulator;

import java.util.Optional;

interface HasOneCheckoutHandler {
    CheckoutHandler getCheckoutHandler();

    Optional<Event[]> finishServingCustomer(double time);
}
