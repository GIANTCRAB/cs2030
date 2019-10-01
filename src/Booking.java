class Booking implements Comparable<Booking> {
    private final Cab cab;
    private final Ride ride;
    private final Request request;

    Booking(Cab cab, Ride ride, Request request) {
        this.cab = cab;
        this.ride = ride;
        this.request = request;
    }

    public Cab getCab() {
        return this.cab;
    }

    public Request getRequest() {
        return this.request;
    }

    public Ride getRide() {
        return this.ride;
    }

    @Override
    public int compareTo(Booking otherBooking) {
        // compare prices
        final int prices = this.getRide().computeFare(this.getRequest()).compareTo(otherBooking.getRide().computeFare(otherBooking.getRequest()));
        if (prices == 0) {
            // Tie breaker
            return this.getCab().getMinutesAway().compareTo(otherBooking.getCab().getMinutesAway());
        }

        return prices;
    }

    public String toString() {
        return String.format("$%.2f using %s (%s)", this.getRide().computeFareInDecimal(this.getRequest()), this.cab.toString(), this.getRide().toString());
    }
}
