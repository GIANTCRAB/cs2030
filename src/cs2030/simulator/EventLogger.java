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

    /**
     * @param message the message to be logged by the event logger
     */
    @Override
    public void log(String message) {
        this.logInfo.append(message);
    }
}
