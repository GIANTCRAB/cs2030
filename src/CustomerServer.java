public class CustomerServer {
    private final Integer id;
    private final Customer currentlyServing;
    private final Customer currentlyWaitServing;

    public CustomerServer(int id, Customer currentlyServing, Customer currentlyWaitServing) {
        this.id = id;
        this.currentlyServing = currentlyServing;
        this.currentlyWaitServing = currentlyWaitServing;
    }

    public Integer getId() {
        return this.id;
    }

    public int getIntId() {
        return this.id;
    }

    public double getAvailableTime() {
        if (this.getCurrentlyServing() != null) {
            if (this.getCurrentlyWaitServing() != null) {
                return this.getCurrentlyWaitServing().getDoneTime();
            } else {
                return this.getCurrentlyServing().getDoneTime();
            }
        }

        return 0.0;

    }

    public boolean canServe(Customer otherCustomer) {
        return this.getCurrentlyServing() == null || this.getAvailableTime() <= otherCustomer.getServiceStartTime();
    }

    public boolean canWaitServe() {
        return this.getCurrentlyServing() != null && this.getCurrentlyWaitServing() == null;
    }

    public CustomerServer serve(Customer customer) {
        if (this.canServe(customer)) {
            return new CustomerServer(this.getIntId(), customer, this.getCurrentlyWaitServing());
        }

        return null;
    }

    public CustomerServer hasServed(Customer customer) {
        if (this.getCurrentlyWaitServing() != null && customer.getIdInt() == this.getCurrentlyWaitServing().getIdInt()) {
            return new CustomerServer(this.getIntId(), customer, null);
        }

        return this;
    }

    public CustomerServer waitServe(Customer customer) {
        if (this.canWaitServe()) {
            return new CustomerServer(this.getIntId(), this.getCurrentlyServing(), customer);
        }

        return null;
    }

    private Customer getCurrentlyServing() {
        return this.currentlyServing;
    }

    private Customer getCurrentlyWaitServing() {
        return this.currentlyWaitServing;
    }

    @Override
    public String toString() {
        return String.format("Customer served; next service @ %.3f", this.getAvailableTime());
    }
}
