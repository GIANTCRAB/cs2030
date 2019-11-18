package cs2030.simulator;

class EventLogger implements Logger {
    private final StringBuilder logInfo;

    EventLogger() {
        this.logInfo = new StringBuilder();
    }

    @Override
    public String toString() {
        return this.logInfo.toString();
    }

    @Override
    public Logger log(String message) {
        this.logInfo.append(message);
        return this;
    }
}
