package cs2030;

import java.util.List;

public interface HasManyCheckoutHandlers extends CheckoutCounter {
    void addCheckoutHandler(CheckoutHandler checkoutHandler);

    List<CheckoutHandler> getCheckoutHandlers();
}
