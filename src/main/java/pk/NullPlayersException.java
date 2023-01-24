package pk;

import org.apache.logging.log4j.Level;

public class NullPlayersException extends SetupException {

    private final Level severityLevel = Level.FATAL;

    public NullPlayersException(String unknownPlayerMessage) {
        super(unknownPlayerMessage);
    }

    public Level getSeverityLevel() {
        return severityLevel;
    }
}
