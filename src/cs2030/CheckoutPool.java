package cs2030;

public interface CheckoutPool {
    ShopCheckoutQueue getCheckoutQueue();

    void addCheckoutHandler(CheckoutHandler checkoutHandler);

    CheckoutHandler[] getCheckoutHandler();

    CheckoutHandler getFirstCheckoutHandler();
}
