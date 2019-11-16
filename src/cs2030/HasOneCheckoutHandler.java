package cs2030;

public interface HasOneCheckoutHandler extends CheckoutCounter {
    CheckoutHandler getCheckoutHandler();
}
