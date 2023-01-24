import org.apache.logging.log4j.Level;
import pk.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class PiratenKarpen {

    public static void main(String[] args) {

        Logger classLogger = LogManager.getLogger(PiratenKarpen.class);

        System.out.println("Welcome to Piraten Karpen Simulator!");


        try {
            CardDeck deck = new CardDeck();
            Game game = new Game(42, 6000, Player.createAllPlayers(args),deck);
            game.playGame();
            Tracker.printStatsToConsole(game);
        } catch (SetupException exception) {
            ExceptionHandler.handleException(exception);
        } catch (Exception exception1) {
            ExceptionHandler.handleException(exception1);
        }

        System.out.println("That's all folks!");
    }
    
}
