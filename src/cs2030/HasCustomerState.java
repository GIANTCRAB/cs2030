package cs2030;

public interface HasCustomerState {
    CustomerStates getCustomerState();

    void setServed();

    void setWait();

    void setLeave();

    void setDone();
}
