package cs2030.simulator;

/**
 * This is an immutable class that stores stats about the simulation.
 * In particular, the average * waiting time, the number of customer
 * who left, and the number of customers who are served, are stored.
 *
 * @author Ooi Wei Tsang
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
class Statistics {
    /**
     * Sum of time spent waiting for all customers.
     */
    private double totalWaitingTime;

    /**
     * Total number of customers who were served.
     */
    private int totalNumOfServedCustomer;

    /**
     * Total number of customers who left without being served.
     */
    private int totalNumOfLostCustomer;

    public Statistics() {
        this.totalWaitingTime = 0;
        this.totalNumOfServedCustomer = 0;
        this.totalNumOfLostCustomer = 0;
    }

    /**
     * Mark that a customer is served.
     *
     * @return A new cs2030.simulator.Statistics object with updated stats
     */
    public Statistics serveOneCustomer() {
        this.totalNumOfServedCustomer++;
        return this;
    }

    /**
     * Mark that a customer is lost.
     *
     * @return A new cs2030.simulator.Statistics object with updated stats
     */
    public Statistics looseOneCustomer() {
        this.totalNumOfLostCustomer++;
        return this;
    }

    /**
     * Accumulate the waiting time of a customer.
     *
     * @param time The time a customer waited.
     * @return A new cs2030.simulator.Statistics object with updated stats
     */
    public Statistics recordWaitingTime(double time) {
        this.totalWaitingTime += time;
        return this;
    }

    /**
     * Return a string representation of the staistics collected.
     *
     * @return A string containing three numbers: the average
     * waiting time, followed by the number of served customer,
     * followed by the number of lost customer.
     */
    public String toString() {
        return String.format("[%.3f %d %d]",
                totalWaitingTime / totalNumOfServedCustomer,
                totalNumOfServedCustomer, totalNumOfLostCustomer);
    }
}
