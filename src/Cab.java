abstract class Cab {
    private final String carPlateNumber;
    private final Integer minutesAway;
    private final Class<? extends Ride>[] servicesProvided;

    Cab(String carPlateNumber, int minutesAway, Class<? extends Ride>[] servicesProvided) {
        this.carPlateNumber = carPlateNumber;
        this.minutesAway = minutesAway;
        this.servicesProvided = servicesProvided;
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

    public Class<? extends Ride>[] getServicesProvided() {
        return this.servicesProvided;
    }

    public String toString() {
        return String.format("%s (%d minutes away) %s", this.getCarPlateNumber(), this.getMinutesAway(), this.getCabType());
    }
}
