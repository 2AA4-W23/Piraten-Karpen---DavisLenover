package pk;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class PlayerRandom extends Player {

    Logger classLogger = LogManager.getLogger(PlayerRandom.class);
    public PlayerRandom(String playerName, int numberOfDice) {
        super(playerName, numberOfDice);
    }

    // Implement strategy
    // Method to keep random dice given the current roll
    public void strategy() {

        int max = super.getCurrentRoll().size();
        int min = 0;

        DevTools.logMessage(this.classLogger,super.getPlayerName() + ": The size of the current roll is " + max, Level.DEBUG);

        // Generate a number between 0 and the number of dice still in play for the player
        Random random = new Random();
        int numberOfRollsToTake = random.nextInt((max-min)+1) + min;
        DevTools.logMessage(this.classLogger,super.getPlayerName() + ": Number of dice to keep is " + numberOfRollsToTake, Level.DEBUG);

        // For the number of rolls we will take, choose a random position from the current roll array list to take
        for (int iterate = 0; iterate < numberOfRollsToTake; iterate++) {
            // Get a random position from current roll to take
            int positionToTake = random.nextInt(super.getCurrentRoll().size());
            DevTools.logMessage(this.classLogger,super.getPlayerName() + ": Position of a dice to take: " + positionToTake, Level.DEBUG);
            // Add the roll to the player' kept rolls
            DevTools.logMessage(this.classLogger,super.getPlayerName() + ": Position of roll was of a: " + super.getCurrentRoll().get(positionToTake), Level.DEBUG);
            super.keepRoll(super.getCurrentRoll().get(positionToTake));

        }

        DevTools.logMessage(this.classLogger,super.getPlayerName() + ": The size of keptRolls is now " + super.getKeptRolls().size(), Level.DEBUG);
        DevTools.logMessage(this.classLogger,super.getPlayerName() + ": keptRolls " + super.getKeptRolls(), Level.DEBUG);

        // Decide at random to end turn
        if ((random.nextInt((10-1)+1) + 1) > 8) {
            DevTools.logMessage(this.classLogger,super.getPlayerName() + " has decided to end their turn", Level.DEBUG);
            endTurn();
        }


    }

}
