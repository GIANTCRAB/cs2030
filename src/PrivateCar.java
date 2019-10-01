class PrivateCar extends Cab {
	private static final Class<? extends Ride>[] servicesProvided = new Class[]{JustRide.class};

	PrivateCar(String carPlateNumber, int minutesAway) {
		super(carPlateNumber, minutesAway, PrivateCar.servicesProvided);
	}
}
