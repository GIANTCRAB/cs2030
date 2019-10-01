public class AvailableCabs {
    public static Cab getCab(String cabType, String carPlateNumber, int minutesAway) {
        switch (cabType) {
            case "PrivateCar":
                return new PrivateCar(carPlateNumber, minutesAway);
            case "NormalCab":
            default:
                return new NormalCab(carPlateNumber, minutesAway);
        }
    }
}
