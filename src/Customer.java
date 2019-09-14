public class Customer implements Comparable<Customer> {
    private final Integer id;
    private final double arrivalTime;

    public Customer(int id, double arrivalTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
    }

    public Integer getId() {
        return id;
    }

    private int getIdInt() {
        return id;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    @Override
    public String toString() {
        return String.format("%d arrives at %.3f", this.getIdInt(), this.getArrivalTime());
    }

    @Override
    public int compareTo(Customer customer) {
        return this.getId().compareTo(customer.getId());
    }
}
