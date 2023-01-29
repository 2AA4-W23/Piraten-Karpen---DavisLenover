package pk;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.Level;

public class CommandLineInterface {

    public static void checkArgs(String[] args) throws SetupException {

        CommandLineParser parser = new DefaultParser();
        Options options = new Options();

        Option combo = new Option("c","combo",true, "Add x players with a dice combo strategy to the game");
        Option random = new Option("r","random",true, "Add x players with a random dice strategy to the game");
        Option seaBattle = new Option("s","seabattle",true, "Add x players with a sea battle card strategy to the game");
        Option numberOfGames = new Option("g","games",true, "Number of games to be played during simulation");
        Option trace = new Option("ta","traceActive",false, "Enable tracing of player moves and general game statistics");

        options.addOption(combo);
        options.addOption(random);
        options.addOption(seaBattle);
        options.addOption(numberOfGames);
        options.addOption(trace);

        try {
            CommandLine commandLine = parser.parse(options,args);
            System.out.println(commandLine.getOptionValue(combo));


        } catch (Exception e) {
            throw new NullPlayersException("Failed to parse arguments");
        }


    }

}
