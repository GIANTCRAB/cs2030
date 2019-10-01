abstract class Ride {
    protected int surchargeCost = 0;
    protected int distanceCost = 0;
    protected int bookingFee = 0;
    protected boolean costSharedWithPassengers = false;

    Ride() {
    }

    private int getSurchargeCost(Request request) {
        if (request.getTime() >= 600 && request.getTime() <= 900) {
            return this.surchargeCost;
        }

        return 0;
    }

    public Integer computeFare(Request request) {
        final int totalCost = this.bookingFee + request.getDistance() * this.distanceCost + this.getSurchargeCost(request);
        // divide by 100 and multiply again to 'absorb' decimal cost
        return this.costSharedWithPassengers ? totalCost / request.getNoOfPassengers() / 100 * 100 : totalCost;
    }

    public double computeFareInDecimal(Request request) {
        return this.computeFare(request).doubleValue() / 100;
    }

    public String getRideType() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return this.getRideType();
    }
}
