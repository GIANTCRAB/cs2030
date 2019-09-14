public enum CustomerStates {
    ARRIVES, SERVED, LEAVES, DONE;

    public String getStateInLowerCaseString() {
        return this.toString().toLowerCase();
    }
}
