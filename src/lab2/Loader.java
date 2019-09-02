package lab2;

public final class Loader {
    private final int id;
    private final Cruise currentlyServing;

    public Loader(int id) {
        this(id, null);
    }

    public Loader(int id, Cruise currentlyServing) {
        this.id = id;
        this.currentlyServing = currentlyServing;
    }

    public Loader serve(Cruise cruise) {
        if (this.currentlyServing != null && this.currentlyServing.doesTimelineConflict(cruise)) {
            return null;
        }

        return new Loader(this.id, cruise);
    }

    @Override
    public String toString() {
        if (this.currentlyServing != null) {
            return "Loader " + this.id + " serving " + this.currentlyServing;
        }
        return "Loader " + this.id;
    }
}
