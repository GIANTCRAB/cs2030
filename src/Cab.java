import java.util.Collection;

abstract class Cab {
    private final String carPlateNumber;
    private final Integer minutesAway;
    private final Collection<Class<? extends Ride>> servicesProvided;

    Cab(String carPlateNumber, int minutesAway, Collection<Class<? extends Ride>> servicesProvided) {
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

    public boolean canProvideService(Ride ride) {
        return this.servicesProvided.contains(ride);
    }

    public String toString() {
        return String.format("%s (%d mins away) %s", this.getCarPlateNumber(), this.getMinutesAway(), this.getCabType());
    }
}
