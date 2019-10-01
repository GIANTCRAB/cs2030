import java.util.Arrays;
import java.util.Collection;

class NormalCab extends Cab {
    private static final Collection<Class<? extends Ride>> servicesProvided = Arrays.asList(JustRide.class, TakeACab.class);

    NormalCab(String carPlateNumber, int minutesAway) {
        super(carPlateNumber, minutesAway, NormalCab.servicesProvided);
    }
}
