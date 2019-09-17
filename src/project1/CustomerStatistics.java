package project1;

public class CustomerStatistics {
    private double totalWaitingTime;
    private int numberOfCustomersServed;
    private int numberOfCustomersLeaves;

    public CustomerStatistics() {
        this.totalWaitingTime = 0.0;
        this.numberOfCustomersServed = 0;
        this.numberOfCustomersLeaves = 0;
    }

    public double computeAverageWaitingTime() {
        return this.getTotalWaitingTime() / this.getNumberOfCustomersServed();
    }

    public double getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public void incrementTotalWaitingTime(double incrementWaitTime) {
        this.totalWaitingTime += incrementWaitTime;
    }

    public int getNumberOfCustomersServed() {
        return numberOfCustomersServed;
    }

    public void incrementNumberOfCustomersServed() {
        this.numberOfCustomersServed++;
    }

    public int getNumberOfCustomersLeaves() {
        return numberOfCustomersLeaves;
    }

    public void incrementNumberOfCustomersLeaves() {
        this.numberOfCustomersLeaves++;
    }
}
