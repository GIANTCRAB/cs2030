package cs2030;

public class EventLogger implements Logger {
    private final StringBuilder logInfo;

    EventLogger() {
        this(new StringBuilder());
    }

    EventLogger(StringBuilder logInfo) {
        this.logInfo = logInfo;
    }

    @Override
    public String toString() {
        return this.logInfo.toString();
    }

    @Override
    public Logger log(String message) {
        return new EventLogger(this.logInfo.append(message));
    }
}
