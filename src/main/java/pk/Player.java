package pk;

import java.util.ArrayList;
import java.util.Random;

public class Player {

    // Declare variables
    private ArrayList<Dice> dices;
    private int points;
    private ArrayList<Faces> currentRoll;
    private ArrayList<Faces> keptRolls;

    private int mainNumberOfDice;

    // Constructor
    public Player(int numberOfDice) {

        // Initialize variables
        this.points = 0;
        this.mainNumberOfDice = numberOfDice;
        this.currentRoll = new ArrayList<Faces>();
        // Create number of dice needed
        this.dices = new ArrayList<Dice>();
        this.keptRolls = new ArrayList<Faces>();
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
    }

    // Method to keep random dice given the current roll
    // For the sake of time (as outlined in step #2), this assumes that there is at minimum two dice
    public void keepRandomDice() {
        Random random = new Random();
        // First, check how many dice the player rolled (as we dont want to choose to take something like 5 dice when we only have 3)
        int max = this.currentRoll.size();
        int min = 0;
        // Randomly choose have many dice to keep
        int diceToTake = random.nextInt((max - min) + 1) + min;
        // Get random positions of dice to keep
        // Create list of integers (wrapper class)
        ArrayList<Integer> dicePositions = new ArrayList<Integer>();

        // Determine which dice (or rather "roll") to take
        while(dicePositions.size() != diceToTake) {
            // Randomly generate a position in the dice array list to take from
            int position = random.nextInt((max - 1) + 1);
            // Check if the position does not exist (the case when we need to take multiple dice)
            if (!dicePositions.contains(position)) {
                // If it doesn't, add the position to the position array list
                dicePositions.add(position);
            }
        }

        // "Move" the dice chosen to be kept
        for (int position : dicePositions) {
            // Add roll to kept rolls
            this.keptRolls.add(currentRoll.get(position));
            // Remove a dice from main dice array list
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

    // Setters
    public void setPoints(int points) {
        this.points = points;
    }
    public void addPoints(int points) {
        this.points += points;
    }
    public void subPoints(int points) {
        this.points -= points;
    }

}
