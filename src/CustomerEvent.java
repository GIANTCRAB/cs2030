public class CustomerEvent implements Comparable<CustomerEvent> {
    private final Customer customer;
    private final double eventTime;
    private final CustomerStates eventAction;

    public CustomerEvent(Customer customer, double eventTime, CustomerStates eventAction) {
        this.customer = customer;
        this.eventTime = eventTime;
        this.eventAction = eventAction;
    }

    public Customer consumeEvent() {
        return this.getCustomer().setState(this.eventAction);
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public CustomerStates getEventAction() {
        return this.eventAction;
    }

    /**
     * TODO: Add time into consideration for prioritization
     *
     * @param customerEvent CustomerEvent
     * @return int
     */
    @Override
    public int compareTo(CustomerEvent customerEvent) {
        return this.getCustomer().getId().compareTo(customerEvent.getCustomer().getId());
    }

    @Override
    public String toString() {
        return String.format("%.3f %d %s", this.eventTime, this.getCustomer().getId(), this.eventAction.getStateInLowerCaseString());
    }
}
