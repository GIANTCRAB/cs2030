import java.util.Arrays;
import java.util.Collection;

class PrivateCar extends Cab {
    private static final Collection<Class<? extends Ride>> servicesProvided = Arrays.asList(JustRide.class, TakeACab.class);

    PrivateCar(String carPlateNumber, int minutesAway) {
        super(carPlateNumber, minutesAway, PrivateCar.servicesProvided);
    }
}
