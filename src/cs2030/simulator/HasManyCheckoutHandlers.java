package cs2030.simulator;

import java.util.List;

interface HasManyCheckoutHandlers extends CheckoutCounter {
    void addCheckoutHandler(CheckoutHandler checkoutHandler);

    List<CheckoutHandler> getCheckoutHandlers();
}
