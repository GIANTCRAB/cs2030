class Request {
    private final int distance;
    private final int noOfPassengers;
    private final int time;

    Request(int distance, int noOfPassengers, int time) {
        this.distance = distance;
        this.noOfPassengers = noOfPassengers;
        this.time = time;
    }

    public int getDistance() {
        return this.distance;
    }

    public int getNoOfPassengers() {
        return this.noOfPassengers;
    }

    public int getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        return String.format("%dkm for %dpax @ %dhrs", this.getDistance(), this.getNoOfPassengers(), this.getTime());
    }
}
