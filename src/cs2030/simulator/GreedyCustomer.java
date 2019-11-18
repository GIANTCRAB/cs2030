package cs2030.simulator;

/**
 * A greedy form of customer that will go to the shortest queue
 */
class GreedyCustomer extends Customer {
    GreedyCustomer(double timeArrived, int id) {
        super(timeArrived, id);
    }

    @Override
    public String toString() {
        return super.toString() + "(greedy)";
    }
}
