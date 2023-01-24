package pk;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DevTools {

    private final static Logger classLogger = LogManager.getLogger(DevTools.class);

    // Logging
    private static boolean isLogging = false;

    public static void logMessage(Logger logger, String message, Level level) {
        if (isLogging) {
            logger.log(level,message);
        }
    }

    public static void enableLogging() {
        if (!isLogging) {
            isLogging = true;
            logMessage(classLogger, "Trace mode has been enabled!", Level.INFO);
        }
    }

    public static boolean isLoggingEnabled() {
        if (isLogging) {
            return true;
        } else {
            return false;
        }
    }

}
