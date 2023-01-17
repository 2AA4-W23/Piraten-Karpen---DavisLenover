package pk;

import java.util.Random;

public class PlayerRandom extends Player {
    public PlayerRandom(String playerName, int numberOfDice) {
        super(playerName, numberOfDice);
    }

    // Implement strategy
    // Method to keep random dice given the current roll
    public void strategy() {

        int max = super.getCurrentRoll().size();
        int min = 0;

        // Generate a number between 0 and the number of dice still in play for the player
        Random random = new Random();
        int numberOfRollsToTake = random.nextInt(max-min) + min;

        // For the number of rolls we will take, choose a random position from the current roll array list to take
        for (int iterate = 0; iterate < numberOfRollsToTake; iterate++) {
            // Get a random position from current roll to take
            int positionToTake = random.nextInt(super.getCurrentRoll().size()-1);
            // Add the roll to the player' kept rolls
            super.getKeptRolls().add(super.getCurrentRoll().get(positionToTake));
            // Remove the roll from the current roll
            super.getCurrentRoll().remove(positionToTake);
            // Remove one dice from the player
            super.getDices().remove(0);

        }


    }

}
