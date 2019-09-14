import java.util.Comparator;

class CustomerEventComparator implements Comparator<CustomerEvent> {
    public int compare(CustomerEvent customerEventOne, CustomerEvent customerEventTwo) {
        if (customerEventOne.getEventTime() < customerEventTwo.getEventTime()) {
            return -1;
        }
        if (customerEventOne.getEventTime() == customerEventTwo.getEventTime()) {
            return customerEventOne.getCustomer().getId().compareTo(customerEventTwo.getCustomer().getId());
        }

        return 1;
    }
}