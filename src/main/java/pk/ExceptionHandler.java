package pk;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;

public class ExceptionHandler {

    static Logger classLogger = LogManager.getLogger(ExceptionHandler.class);

    // Handle setup exceptions
    public static void handleException(SetupException exception) {

        boolean isFatal = false;

        DevTools.logMessage(classLogger,"A SetupException occured!" + " Exception: " + exception.toString(),exception.getServerityLevel());

        if (exception.getServerityLevel() == Level.FATAL) {
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

        DevTools.logMessage(classLogger,"A GameException occured!" + " Exception: " + exception.toString(),exception.getServerityLevel());

        if (exception.getServerityLevel() == Level.FATAL) {
            isFatal = true;
        } else if (exception.getServerityLevel() == Level.WARN) {

            // Check for all GameExceptions that have a level of warn
            if (exception instanceof EmptyDeckException) {
                DevTools.logMessage(classLogger,"Re-filling card deck..." + exception.toString(),Level.INFO);

                try {
                    ((EmptyDeckException) exception).getCardDeck().fillDeck();
                } catch (Exception newException) {
                    ExceptionHandler.handleException(newException);
                }

            }
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

        DevTools.logMessage(classLogger,"A general exception occured!" + " Exception: " + exception.toString(),Level.FATAL);

        printStackTrace(exception);

        DevTools.logMessage(classLogger,"Error was fatal, as such, program will not continue...",Level.FATAL);
        System.exit(0);

    }



    // Print setup exception stack traces
    private static void printStackTrace(SetupException exception) {
        DevTools.logMessage(classLogger,"|----------StackTrace----------|",exception.getServerityLevel());
        for (int index = 0; index < exception.getStackTrace().length; index++) {
            DevTools.logMessage(classLogger, String.valueOf(exception.getStackTrace()[index]),exception.getServerityLevel());
        }
        DevTools.logMessage(classLogger,"|----------End StackTrace----------|",exception.getServerityLevel());
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
