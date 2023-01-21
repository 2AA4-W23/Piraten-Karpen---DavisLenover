package pk;

import org.apache.logging.log4j.Level;

public abstract class SetupException extends Exception {

    public SetupException(String message) {
        super(message);
    }

    public abstract Level getServerityLevel();

}
