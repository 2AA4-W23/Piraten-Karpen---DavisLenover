package pk;

import org.apache.logging.log4j.Level;

public class BadNumberOfGamesException extends SetupException {

    private final Level severityLevel = Level.FATAL;

    public BadNumberOfGamesException(String message) {
        super(message);
    }

    public Level getSeverityLevel() {
        return severityLevel;
    }
}
