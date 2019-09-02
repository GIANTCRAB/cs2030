package lab2;

import java.time.LocalTime;

public class Cruise {
    private final String id;
    private final LocalTime arrivalTime;
    private final int loadersRequired;
    private final int loadingMinutes;

    public Cruise(String id, int arrivalTime) {
        this(id, arrivalTime, 1, 30);
    }

    public Cruise(String id, int arrivalTime, int loadersRequired, int loadingMinutes) {
        this.id = id;
        this.arrivalTime = calculateArrivalTime(arrivalTime);
        this.loadersRequired = loadersRequired;
        this.loadingMinutes = loadingMinutes;
    }

    public int getArrivalTime() {
        final int MINUTES_IN_AN_HOUR = 60;
        final int hoursInMinutes = arrivalTime.getHour() * MINUTES_IN_AN_HOUR;

        return arrivalTime.getMinute() + hoursInMinutes;
    }

    public String getArrivalTimeDisplay() {
        return String.format("%02d", arrivalTime.getHour()) + String.format("%02d", arrivalTime.getMinute());
    }

    private LocalTime calculateArrivalTime(int arrivalTime) {
        String formattedArrivalTime = String.format("%04d", arrivalTime);
        int hour = Integer.parseInt(formattedArrivalTime.substring(0, 2));
        int minute = Integer.parseInt(formattedArrivalTime.substring(2, 4));
        return LocalTime.of(hour, minute);
    }

    public boolean doesTimelineConflict(Cruise otherCruise) {
        return (this.getArrivalTime() >= otherCruise.getArrivalTime() && this.getArrivalTime() < otherCruise.getServiceCompletionTime())
                || (this.getServiceCompletionTime() > otherCruise.getArrivalTime() && this.getServiceCompletionTime() < otherCruise.getServiceCompletionTime()
                || (otherCruise.getArrivalTime() >= this.getArrivalTime() && otherCruise.getArrivalTime() < this.getServiceCompletionTime())
                || (otherCruise.getServiceCompletionTime() > this.getArrivalTime() && otherCruise.getServiceCompletionTime() < this.getServiceCompletionTime()));
    }

    public String getId() {
        return id;
    }

    public int getNumLoadersRequired() {
        return loadersRequired;
    }

    public int getServiceCompletionTime() {
        return this.getArrivalTime() + this.getLoadingMinutes();
    }

    public int getLoadingMinutes() {
        return this.loadingMinutes;
    }

    @Override
    public String toString() {
        return this.getId() + "@" + this.getArrivalTimeDisplay();
    }
}
