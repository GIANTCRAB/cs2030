package cs2030;

public interface HasOneCheckoutHandler extends CheckoutCounter {
    void setCheckoutHandler(CheckoutHandler checkoutHandler);

    CheckoutHandler getCheckoutHandler();
}
