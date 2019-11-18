package cs2030.simulator;

class GreedyCustomer extends Customer {
    GreedyCustomer(double timeArrived, int id) {
        super(timeArrived, id);
    }

    @Override
    public String toString() {
        return super.toString() + "(greedy)";
    }
}
