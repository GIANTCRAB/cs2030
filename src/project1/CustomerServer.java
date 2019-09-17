package project1;

public class CustomerServer {
    private final Integer id;
    private Customer currentlyServing;
    private Customer currentlyWaitServing;

    public CustomerServer(int id, Customer currentlyServing, Customer currentlyWaitServing) {
        this.id = id;
        this.currentlyServing = currentlyServing;
        this.currentlyWaitServing = currentlyWaitServing;
    }

    public Integer getId() {
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
            this.currentlyServing = customer;
            return this;
        }

        return null;
    }

    public CustomerServer hasServed(Customer customer) {
        if (this.getCurrentlyWaitServing() != null && customer == this.getCurrentlyWaitServing()) {
            this.currentlyServing = customer;
            this.currentlyWaitServing = null;
        }

        return this;
    }

    public CustomerServer waitServe(Customer customer) {
        if (this.canWaitServe()) {
            this.currentlyWaitServing = customer;
            return this;
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
