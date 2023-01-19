import org.apache.logging.log4j.Level;
import pk.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class PiratenKarpen {

    public static void main(String[] args) {

        Logger classLogger = LogManager.getLogger(PiratenKarpen.class);

        // Setup logger
        // Users of program can enable logging by using the runtime argument "traceActive" in console
        if (args.length == 1) {
            if (args[0].contains("traceActive")) {
                Tracker.isLogging = true;
                Tracker.logMessage(classLogger,"Trace mode active, outputs will be displayed in console!", Level.INFO);
            }
        }

        System.out.println("Welcome to Piraten Karpen Simulator!");

        Tracker.logMessage(classLogger,"Creating player Bob with 8 Dice...",Level.DEBUG);
        Player player1 = new PlayerRandom("Bob",8);
        Tracker.logMessage(classLogger,"Creating player Jim with 8 Dice...",Level.DEBUG);
        Player player2 = new PlayerRandom("Jim",8);

        Tracker.logMessage(classLogger,"Creating game with 42 rounds, 6000 win condition and two players...",Level.DEBUG);
        Game game = new Game(42, 6000, player1,player2);
        game.playGame();
        Tracker.printStatsToConsole(game);

        System.out.println("That's all folks!");
    }
    
}
