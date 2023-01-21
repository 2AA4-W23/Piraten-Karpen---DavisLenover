import org.apache.logging.log4j.Level;
import pk.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class PiratenKarpen {

    public static void main(String[] args) {

        Logger classLogger = LogManager.getLogger(PiratenKarpen.class);

        System.out.println("Welcome to Piraten Karpen Simulator!");

        try {
            Game game = new Game(42, 6000, Player.createAllPlayers(args));
            game.playGame();
            Tracker.printStatsToConsole(game);
        } catch (SetupException exception) {
            ExceptionHandler.handleException(exception);
        }

        System.out.println("That's all folks!");
    }
    
}
