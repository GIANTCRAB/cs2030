package cs2030;

public interface CheckoutPool {
    ShopCheckoutQueue getCheckoutQueue();

    CheckoutHandler[] getCheckoutHandler();

    CheckoutHandler getFirstCheckoutHandler();
}
