package lab2;

public class Loader {
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
        if (this.canServe(cruise)) {
            return new Loader(this.getId(), cruise);
        }

        return null;
    }

    protected boolean canServe(Cruise cruise) {
        return this.getCurrentlyServing() == null || !this.getCurrentlyServing().doesTimelineConflict(cruise);
    }

    @Override
    public String toString() {
        if (this.getCurrentlyServing() != null) {
            return "Loader " + this.getId() + " serving " + this.getCurrentlyServing();
        }
        return "Loader " + this.getId();
    }

    public int getId() {
        return this.id;
    }

    public Cruise getCurrentlyServing() {
        return this.currentlyServing;
    }
}
