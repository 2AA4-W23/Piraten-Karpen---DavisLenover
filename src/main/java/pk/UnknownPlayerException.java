package pk;

import org.apache.logging.log4j.Level;

public class UnknownPlayerException extends SetupException {

    public Level serverityLevel = Level.FATAL;
    public UnknownPlayerException(String unknownPlayerMessage) {
        super(unknownPlayerMessage);
    }

    public Level getServerityLevel() {
        return serverityLevel;
    }
}
