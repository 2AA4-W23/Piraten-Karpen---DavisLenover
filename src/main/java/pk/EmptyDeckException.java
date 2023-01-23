package pk;

import org.apache.logging.log4j.Level;

public class EmptyDeckException extends GameException {

    private final Level serverityLevel = Level.WARN;

    public EmptyDeckException(String exceptionMessage) {

        super(exceptionMessage);

    }

    public Level getServerityLevel() {
        return this.serverityLevel;
    }


}
