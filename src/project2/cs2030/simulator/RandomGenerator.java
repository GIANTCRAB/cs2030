package project2.cs2030.simulator;

import java.util.Random;

class RandomGenerator {
    private final Random rngArrival;
    private final Random rngService;
    private final Random rngRest;
    private final Random rngRestPeriod;
    private final Random rngTimeoutPeriod;
    private final Random rngCustomerType;
    private final double customerArrivalRate;
    private final double customerServiceRate;
    private final double serverRestingRate;

    RandomGenerator(int seed, double customerArrivalRate, double customerServiceRate, double serverRestingRate) {
        this.rngArrival = new Random((long) seed);
        this.rngService = new Random((long) (seed + 1));
        this.rngRest = new Random((long) (seed + 2));
        this.rngRestPeriod = new Random((long) (seed + 3));
        this.rngCustomerType = new Random((long) (seed + 4));
        this.rngTimeoutPeriod = new Random((long) (seed + 5));
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
