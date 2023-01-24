package pk;

import org.apache.logging.log4j.Level;

public class UnknownPlayerException extends SetupException {

    private final Level severityLevel = Level.FATAL;
    public UnknownPlayerException(String unknownPlayerMessage) {
        super(unknownPlayerMessage);
    }

    public Level getSeverityLevel() {
        return severityLevel;
    }
}
