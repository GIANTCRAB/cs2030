abstract class Cab {
    private final String carPlateNumber;
    private final Integer minutesAway;

    Cab(String carPlateNumber, int minutesAway) {
        this.carPlateNumber = carPlateNumber;
        this.minutesAway = minutesAway;
    }

    public String getCarPlateNumber() {
        return this.carPlateNumber;
    }

    public Integer getMinutesAway() {
        return this.minutesAway;
    }

    public String getCabType() {
        return this.getClass().getSimpleName();
    }

    public String toString() {
        return String.format("%s (%d mins away) %s", this.getCarPlateNumber(), this.getMinutesAway(), this.getCabType());
    }
}
