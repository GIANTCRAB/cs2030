package cs2030.simulator;

interface HasCustomerState {
    CustomerStates getCustomerState();

    void setServed();

    void setWait();

    void setLeave();

    void setDone();
}
