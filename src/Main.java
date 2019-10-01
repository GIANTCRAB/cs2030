import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner reader = new Scanner(System.in);
        final Request request = Main.readRequest(reader);
        final Collection<Cab> cabs = Main.readCabs(reader);
        reader.close();

        final Collection<Booking> bookings = Main.createBookings(request, cabs);
        final Booking[] bookingArray = bookings.toArray(new Booking[bookings.size()]);
        Arrays.sort(bookingArray);
        for (Booking booking : bookingArray) {
            System.out.println(booking);
        }
    }

    private static Request readRequest(Scanner reader) {
        return new Request(reader.nextInt(), reader.nextInt(), reader.nextInt());
    }

    private static Collection<Cab> readCabs(Scanner reader) {
        final Collection<Cab> cabs = new ArrayList<>();

        while (reader.hasNext()) {
            final String cabType = reader.next();
            final String carPlateNumber = reader.next();
            final int minutesAway = reader.nextInt();
            final Cab cab = AvailableCabs.getCab(cabType, carPlateNumber, minutesAway);

            cabs.add(cab);
        }

        return cabs;
    }

    private static Collection<Booking> createBookings(Request request, Collection<Cab> cabs) {
        final Collection<Class<? extends Ride>> rideTypes = AvailableRides.getAllRideTypes();
        final Collection<Booking> bookingArrayList = new ArrayList<>();
        for (Cab cab : cabs) {
            for (Class<? extends Ride> rideType : rideTypes) {
                if (cab.canProvideService(rideType)) {
                    try {
                        final Ride ride = rideType.newInstance();
                        bookingArrayList.add(new Booking(cab, ride, request));
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return bookingArrayList;
    }
}
