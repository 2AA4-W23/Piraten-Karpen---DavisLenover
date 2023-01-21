package pk;

import org.apache.logging.log4j.Level;

public class NullPlayersException extends SetupException {

    private final Level serverityLevel = Level.FATAL;

    public NullPlayersException(String unknownPlayerMessage) {
        super(unknownPlayerMessage);
    }

    public Level getServerityLevel() {
        return serverityLevel;
    }
}
