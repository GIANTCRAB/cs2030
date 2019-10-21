package lab7;

class Statistics {
    /**
     * Sum of time spent waiting for all customers.
     */
    private final double totalWaitingTime;

    /**
     * Total number of customers who were served.
     */
    private final int totalNumOfServedCustomers;

    /**
     * Total number of customers who left without being served.
     */
    private final int totalNumOfLostCustomers;

    /**
     * Construct an lab7.Statistics object with 0 waiting time, 0
     * served customer, and 0 lost customer.
     *
     * @return A new lab7.Statistics object
     */
    public Statistics() {
        this(0, 0, 0);
    }

    public Statistics(double totalWaitingTime, int totalNumOfServedCustomers, int totalNumOfLostCustomers) {
        this.totalWaitingTime = totalWaitingTime;
        this.totalNumOfServedCustomers = totalNumOfServedCustomers;
        this.totalNumOfLostCustomers = totalNumOfLostCustomers;
    }

    /**
     * Mark that a customer is served.
     *
     * @return A new lab7.Statistics object with updated stats
     */
    public Statistics serveOneCustomer() {
        final int totalNumOfServedCustomers = this.totalNumOfServedCustomers + 1;
        return new Statistics(this.totalWaitingTime, totalNumOfServedCustomers, this.totalNumOfLostCustomers);
    }

    /**
     * Mark that a customer is lost.
     *
     * @return A new lab7.Statistics object with updated stats
     */
    public Statistics looseOneCustomer() {
        final int totalNumOfLostCustomers = this.totalNumOfLostCustomers + 1;
        return new Statistics(this.totalWaitingTime, this.totalNumOfServedCustomers, totalNumOfLostCustomers);
    }

    /**
     * Accumulate the waiting time of a customer.
     *
     * @param time The time a customer waited.
     * @return A new lab7.Statistics object with updated stats
     */
    public Statistics recordWaitingTime(double time) {
        final double totalWaitingTime = this.totalWaitingTime + time;
        return new Statistics(totalWaitingTime, this.totalNumOfServedCustomers, this.totalNumOfLostCustomers);
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
                totalWaitingTime / totalNumOfServedCustomers,
                totalNumOfServedCustomers, totalNumOfLostCustomers);
    }
}
