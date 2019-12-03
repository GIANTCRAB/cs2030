package project2.cs2030.simulator;

import java.util.Optional;
import java.util.Set;

interface HasManyCheckoutHandlers {
    Set<CheckoutHandler> getCheckoutHandlers();

    Optional<Event[]> finishServingCustomer(double time, CheckoutHandler selfCheckoutMachine);
}
