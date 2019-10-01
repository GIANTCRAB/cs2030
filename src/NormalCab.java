class NormalCab extends Cab {
	private static final Class<? extends Ride>[] servicesProvided = new Class[]{JustRide.class, TakeACab.class};

	NormalCab(String carPlateNumber, int minutesAway) {
		super(carPlateNumber, minutesAway, NormalCab.servicesProvided);
	}
}
