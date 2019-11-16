package cs2030;

public enum CustomerStates {
    ARRIVES, SERVED, WAITS, LEAVES, DONE;

    public String getStateInLowerCaseString() {
        return this.toString().toLowerCase();
    }
}
