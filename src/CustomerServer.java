public class CustomerServer {
    private final Integer id;
    private final Customer currentlyServing;

    public CustomerServer(int id, Customer currentlyServing) {
        this.id = id;
        this.currentlyServing = currentlyServing;
    }

    public Integer getId() {
        return this.id;
    }

    public int getIntId() {
        return this.id;
    }

    public double getAvailableTime() {
        return this.getCurrentlyServing().getDoneTime();
    }

    public boolean canServe(Customer otherCustomer) {
        return this.getCurrentlyServing() == null || this.getAvailableTime() <= otherCustomer.getArrivalTime();
    }

    public CustomerServer serve(Customer customer) {
        if (this.canServe(customer)) {
            return new CustomerServer(this.getIntId(), customer);
        }

        return null;
    }

    private Customer getCurrentlyServing() {
        return this.currentlyServing;
    }

    @Override
    public String toString() {
        return String.format("Customer served; next service @ %.3f", this.getAvailableTime());
    }
}
