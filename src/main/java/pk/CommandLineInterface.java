package pk;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandLineInterface {

    public static Game checkArgs(String[] args) throws Exception {

        CommandLineParser parser = new DefaultParser();
        Options options = new Options();

        Option combo = new Option("c", "combo", true, "Add x players with a dice combo strategy to the game");
        Option random = new Option("r", "random", true, "Add x players with a random dice strategy to the game");
        Option seaBattle = new Option("s", "seabattle", true, "Add x players with a sea battle card strategy to the game");
        Option numberOfGames = new Option("g", "games", true, "Number of games to be played during simulation");
        Option trace = new Option("ta", "traceActive", false, "Enable tracing of player moves and general game statistics");

        options.addOption(combo);
        options.addOption(random);
        options.addOption(seaBattle);
        options.addOption(numberOfGames);
        options.addOption(trace);


        CommandLine commandLine = parser.parse(options, args);

        // Check if traceMode is to be activated
        if (commandLine.hasOption(trace)) {
            DevTools.enableLogging();
        }

        // Create players
        // Create hashmap to pass into Player class method
        HashMap<PlayerType, Integer> playersToBeCreated = new HashMap<>();
        // Get command line values for how many of each player there should be (default is 0)
        playersToBeCreated.put(PlayerType.COMBO, Integer.valueOf(commandLine.getOptionValue(combo, String.valueOf(0))));
        playersToBeCreated.put(PlayerType.RANDOM, Integer.valueOf(commandLine.getOptionValue(random, String.valueOf(0))));
        playersToBeCreated.put(PlayerType.SEABATTLE, Integer.valueOf(commandLine.getOptionValue(seaBattle, String.valueOf(0))));
        // Get resulting player list from Player class
        ArrayList<Player> playerList = Player.createAllPlayers(playersToBeCreated);

        // Get number of games
        int numOfGames = Integer.parseInt(commandLine.getOptionValue(numberOfGames, String.valueOf(42)));

        // Create card deck
        CardDeck deck = new CardDeck();

        // Create game
        return new Game(numOfGames,6000,playerList,deck);

    }

}
