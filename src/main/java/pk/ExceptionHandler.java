package pk;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExceptionHandler {

    static Logger classLogger = LogManager.getLogger(ExceptionHandler.class);

    // Handle setup exceptions
    public static void handleException(SetupException exception) {

        if (!DevTools.isLoggingEnabled()) {
            System.out.println("Whoops! We ran into a problem!");
            System.out.println("For more information, please make sure tracing is enabled via passing 'traceActive' as an argument.");
        }

        boolean isFatal = false;

        DevTools.logMessage(classLogger,"A SetupException occured!" + " Exception: " + exception.toString(),exception.getServerityLevel());

        printStackTrace(exception);

        if (exception.getServerityLevel() == Level.FATAL) {
            isFatal = true;
        }

        if (isFatal) {
            DevTools.logMessage(classLogger,"Error was fatal, as such, program will not continue...",Level.FATAL);
            System.exit(0);
        }

    }

    // Print setup exception stack traces
    private static void printStackTrace(SetupException exception) {
        DevTools.logMessage(classLogger,"|----------StackTrace----------|",exception.getServerityLevel());
        for (int index = 0; index < exception.getStackTrace().length; index++) {
            DevTools.logMessage(classLogger, String.valueOf(exception.getStackTrace()[index]),exception.getServerityLevel());
        }
        DevTools.logMessage(classLogger,"|----------End StackTrace----------|",exception.getServerityLevel());
    }

}
