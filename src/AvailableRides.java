import java.util.Arrays;
import java.util.Collection;

public class AvailableRides {
    private static final Collection<Class<? extends Ride>> rideTypes = Arrays.asList(JustRide.class, TakeACab.class, ShareARide.class);

    public static Collection<Class<? extends Ride>> getAllRideTypes() {
        return AvailableRides.rideTypes;
    }
}
