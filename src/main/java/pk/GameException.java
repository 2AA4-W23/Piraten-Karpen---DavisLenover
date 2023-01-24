package pk;

import org.apache.logging.log4j.Level;

public abstract class GameException extends Exception {

    public GameException(String message) {
        super(message);
    }

    public abstract Level getSeverityLevel();
}
