package lab2;

public class RecycledLoader extends Loader {
    private int ADDITIONAL_DOWNTIME_IN_MINUTES = 60;

    public RecycledLoader(int id) {
        this(id, null);
    }

    public RecycledLoader(int id, Cruise cruise) {
        super(id, cruise);
    }

    public RecycledLoader serve(Cruise cruise) {
        Cruise modifiedCruise = new Cruise(
                cruise.getId(),
                Integer.parseInt(cruise.getArrivalTimeDisplay()),
                cruise.getNumLoadersRequired(),
                cruise.getLoadingMinutes() + ADDITIONAL_DOWNTIME_IN_MINUTES
        );

        if (this.canServe(modifiedCruise)) {
            return new RecycledLoader(this.getId(), modifiedCruise);
        }

        return null;
    }

    @Override
    public String toString() {
        if (this.getCurrentlyServing() != null) {
            return "Loader " + this.getId() + " (recycled) serving " + this.getCurrentlyServing();
        }
        return "Loader " + this.getId() + " (recycled)";
    }
}
