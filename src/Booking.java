class Booking implements Comparable<Booking> {
	private Cab cab;
	private Ride ride;
	private Request request;

	Booking (Cab cab, Ride ride, Request request) {
		this.cab = cab;
		this.ride = ride;
		this.request = request;
	}

	public Request getRequest() {
		return this.request;
	}

	public Ride getRide() {
		return this.ride;
	}

	@Override
	public int compareTo(Booking otherBooking) {
		// Tie breaker
		return this.getRide().computeFare(this.getRequest()).compareTo(otherBooking.getRide().computeFare(otherBooking.getRequest()));
	}

	public String toString() {
		return String.format("$%.2f using %s (%s)", this.getRide().computeFareInDecimal(this.getRequest()), this.cab.toString(), this.getRide().toString());
	}
}
