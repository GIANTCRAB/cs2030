package cs2030.simulator;

import java.util.Random;

public class RandomGenerator {
    private final Random rngArrival;
    private final Random rngService;
    private final Random rngRest;
    private final Random rngRestPeriod;
    private final Random rngTimeoutPeriod;
    private final Random rngCustomerType;
    private final double customerArrivalRate;
    private final double customerServiceRate;
    private final double serverRestingRate;

    RandomGenerator(int rngArrival, double customerArrivalRate, double customerServiceRate, double serverRestingRate) {
        this.rngArrival = new Random((long) rngArrival);
        this.rngService = new Random((long) (rngArrival + 1));
        this.rngRest = new Random((long) (rngArrival + 2));
        this.rngRestPeriod = new Random((long) (rngArrival + 3));
        this.rngCustomerType = new Random((long) (rngArrival + 4));
        this.rngTimeoutPeriod = new Random((long) (rngArrival + 5));
        this.customerArrivalRate = customerArrivalRate;
        this.customerServiceRate = customerServiceRate;
        this.serverRestingRate = serverRestingRate;
    }

    double genInterArrivalTime() {
        return -Math.log(this.rngArrival.nextDouble()) / this.customerArrivalRate;
    }

    double genServiceTime() {
        return -Math.log(this.rngService.nextDouble()) / this.customerServiceRate;
    }

    double genRandomRest() {
        return this.rngRest.nextDouble();
    }

    double genRestPeriod() {
        return -Math.log(this.rngRestPeriod.nextDouble()) / this.serverRestingRate;
    }

    double genCustomerType() {
        return this.rngCustomerType.nextDouble();
    }
}
