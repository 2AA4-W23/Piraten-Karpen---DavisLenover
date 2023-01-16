package pk;

import java.util.ArrayList;
import java.util.Random;

public class Player {

    // Declare variables
    private ArrayList<Dice> dices;
    private int points;
    private ArrayList<Faces> currentRoll;
    private ArrayList<Faces> keptRolls;

    private int wins;

    private int mainNumberOfDice;

    // Constructor
    public Player(int numberOfDice) {

        // Initialize variables
        this.points = 0;
        this.mainNumberOfDice = numberOfDice;
        this.currentRoll = new ArrayList<Faces>();
        this.dices = new ArrayList<Dice>();
        this.keptRolls = new ArrayList<Faces>();
        this.wins = 0;

        // For loop adding dice to array which holds all dice for a given player
        for (int i = 0; i < numberOfDice; i++) {
            this.dices.add(new Dice());
        }
    }

    // Method to roll all dice the player has
    // What the player rolled is then stored in the currentRoll array
    public void rollDice() {

        // Clear previous roll (if-applicable)
        this.currentRoll.clear();

        // Roll all dices and store their value
        for (Dice currentDie : this.dices) {
            this.currentRoll.add(currentDie.roll());
        }

        // Then add skulls to kept dice
        addSkulls();

    }

    // Method to add all skulls rolled to keptrolls
    private void addSkulls() {

        // Loop through all dice rolled
        for (Faces currentFace : this.currentRoll) {
            // Check if any were a skull
            if (currentFace.equals(Faces.SKULL)) {
                // If so, add it to kept rolls and remove one dice from the player
                this.keptRolls.add(currentFace);
                this.dices.remove(0);
            }
        }

        // Remove any skulls from current roll array list
        this.currentRoll.removeIf(face -> face.equals(Faces.SKULL));

    }

    // Method to keep random dice given the current roll
    public void keepRandomDice() {

        int max = this.currentRoll.size();
        int min = 0;

        // Generate a number between 0 and the number of dice still in play for the player
        Random random = new Random();
        int numberOfRollsToTake = random.nextInt(max-min) + min;

        // For the number of rolls we will take, choose a random position from the current roll array list to take
        for (int iterate = 0; iterate < numberOfRollsToTake; iterate++) {
            // Get a random position from current roll to take
            int positionToTake = random.nextInt(this.currentRoll.size()-1);
            // Add the roll to the player' kept rolls
            this.keptRolls.add(this.currentRoll.get(positionToTake));
            // Remove the roll from the current roll
            currentRoll.remove(positionToTake);
            // Remove one dice from the player
            this.dices.remove(0);

        }


    }

    public void resetDice() {
       this.currentRoll.clear();
       this.keptRolls.clear();
       this.dices.clear();
        for (int i = 0; i < this.mainNumberOfDice; i++) {
            this.dices.add(new Dice());
        }
    }

    // Getters
    public ArrayList<Faces> getCurrentRoll() {
        return this.currentRoll;
    }
    public int getPoints() {
        return this.points;
    }

    public int getNumberOfSkulls() {
        int numberOfSkulls = 0;
        for (Faces currentFace : keptRolls) {
            if (currentFace.equals(Faces.SKULL)) {
                numberOfSkulls++;
            }
        }
        return numberOfSkulls;
    }

    public ArrayList<Dice> getDices() {
        return this.dices;
    }

    public ArrayList<Faces> getKeptRolls() {
        return this.keptRolls;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    // Setters
    public void addPoints(int points) {
        this.points += points;
    }
    public void subPoints(int points) {
        this.points -= points;
    }

}
