package project1;

public class CustomerEvent {
    private final Customer customer;
    private final CustomerServer customerServer;
    private final double eventTime;
    private final CustomerStates eventAction;

    public CustomerEvent(Customer customer, double eventTime, CustomerStates eventAction) {
        this(customer, null, eventTime, eventAction);
    }

    public CustomerEvent(Customer customer, CustomerServer customerServer, double eventTime, CustomerStates eventAction) {
        this.customer = customer;
        this.customerServer = customerServer;
        this.eventTime = eventTime;
        this.eventAction = eventAction;
    }

    public Customer consumeEvent() {
        return this.getCustomer().setState(this.eventAction);
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustomerServer getCustomerServer() {
        return this.customerServer;
    }

    public CustomerStates getEventAction() {
        return this.eventAction;
    }

    public double getEventTime() {
        return this.eventTime;
    }

    @Override
    public String toString() {
        if (this.getCustomerServer() != null) {
            String displayMessage = "%.3f %d %s by %d";
            switch (this.eventAction) {
                case WAITS:
                    displayMessage = "%.3f %d %s to be served by %d";
                    break;
                case DONE:
                    displayMessage = "%.3f %d %s serving by %d";
                    break;
            }
            return String.format(displayMessage, this.eventTime, this.getCustomer().getId(), this.eventAction.getStateInLowerCaseString(), this.getCustomerServer().getId());
        }

        return String.format("%.3f %d %s", this.eventTime, this.getCustomer().getId(), this.eventAction.getStateInLowerCaseString());
    }
}
