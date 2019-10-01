abstract class Cab {
	private final String carPlateNumber;
	private final int minutesAway;

	Cab(String carPlateNumber, int minutesAway) {
		this.carPlateNumber = carPlateNumber;
		this.minutesAway = minutesAway;
	}

	public String getCarPlateNumber() {
		return this.carPlateNumber;
	}

	public int getMinutesAway() {
		return this.minutesAway;
	}

	public String getCabType() {
		return this.getClass().getSimpleName();
	}

	public String toString() {
		return String.format("%s (%d minutes away) %s", this.getCarPlateNumber(), this.getMinutesAway(), this.getCabType());
	}
}
