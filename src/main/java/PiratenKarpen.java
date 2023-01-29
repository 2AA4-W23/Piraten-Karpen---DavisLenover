import org.apache.logging.log4j.Level;
import pk.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class PiratenKarpen {

    public static void main(String[] args) {

        Logger classLogger = LogManager.getLogger(PiratenKarpen.class);

        try {
            Game newGame = CommandLineInterface.checkArgs(args);
            System.out.println("Welcome to Piraten Karpen Simulator!");
            newGame.playGame();
            Tracker.printStatsToConsole(newGame);

        } catch (SetupException exception) {
            ExceptionHandler.handleException(exception);
        } catch (Exception generalException) {
            ExceptionHandler.handleException(generalException);
        }

        System.out.println("That's all folks!");
    }
    
}
