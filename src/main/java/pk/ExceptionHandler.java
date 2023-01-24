package pk;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExceptionHandler {

    private final static Logger classLogger = LogManager.getLogger(ExceptionHandler.class);

    // Handle setup exceptions
    public static void handleException(SetupException exception) {

        boolean isFatal = false;

        DevTools.logMessage(classLogger,"A SetupException occurred!" + " Exception: " + exception.toString(),exception.getSeverityLevel());

        if (exception.getSeverityLevel() == Level.FATAL) {
            // If logging is not enabled then it is useful to display important messages via print
            if (!DevTools.isLoggingEnabled()) {
                System.out.println("Whoops! We ran into a problem!");
                if (exception instanceof UnknownPlayerException) {
                    System.out.println("It seems we cannot understand the passed arguments!");
                    System.out.println(exception.getMessage());
                } else if (exception instanceof NullPlayersException) {
                    System.out.println("It seems we could not detect any passed arguments!");
                    System.out.println(exception.getMessage());
                }
                System.out.println("For more information, please make sure tracing is enabled via passing 'traceActive' as the first argument.");
            }

            isFatal = true;
        }

        if (isFatal) {
            DevTools.logMessage(classLogger,"Error was fatal, as such, program will not continue...",Level.FATAL);
            System.exit(0);
        }

    }

    // Handle game exceptions
    public static void handleException(GameException exception) {

        boolean isFatal = false;

        DevTools.logMessage(classLogger,"A GameException occurred!" + " Exception: " + exception.toString(),exception.getSeverityLevel());

        if (exception.getSeverityLevel() == Level.FATAL) {
            isFatal = true;
        } else if (exception.getSeverityLevel() == Level.WARN) {

        }

        if (isFatal) {
            DevTools.logMessage(classLogger,"Error was fatal, as such, program will not continue...",Level.FATAL);
            System.exit(0);
        }

    }


    // Handle general exceptions
    public static void handleException(Exception exception) {

        if (!DevTools.isLoggingEnabled()) {
            System.out.println("Whoops! We ran into a problem!");
            System.out.println("For more information, please make sure tracing is enabled via passing 'traceActive' as the first argument.");
        }

        DevTools.logMessage(classLogger,"A general exception occurred!" + " Exception: " + exception.toString(),Level.FATAL);

        DevTools.logMessage(classLogger,"Error was fatal, as such, program will not continue...",Level.FATAL);
        System.exit(0);

    }



    // Print setup exception stack traces
    private static void printStackTrace(SetupException exception) {
        DevTools.logMessage(classLogger,"|----------StackTrace----------|",exception.getSeverityLevel());
        for (int index = 0; index < exception.getStackTrace().length; index++) {
            DevTools.logMessage(classLogger, String.valueOf(exception.getStackTrace()[index]),exception.getSeverityLevel());
        }
        DevTools.logMessage(classLogger,"|----------End StackTrace----------|",exception.getSeverityLevel());
    }

    // Print stack trace for general exceptions
    private static void printStackTrace(Exception exception) {
        DevTools.logMessage(classLogger,"|----------StackTrace----------|",Level.FATAL);
        for (int index = 0; index < exception.getStackTrace().length; index++) {
            DevTools.logMessage(classLogger, String.valueOf(exception.getStackTrace()[index]),Level.FATAL);
        }
        DevTools.logMessage(classLogger,"|----------End StackTrace----------|",Level.FATAL);
    }

}
