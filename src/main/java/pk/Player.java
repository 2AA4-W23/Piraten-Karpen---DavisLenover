package pk;

import java.util.ArrayList;
import java.util.Random;

public class Player {

    // Declare variables
    private ArrayList<Dice> dices;
    private int points;
    private ArrayList<Faces> currentRoll;
    private ArrayList<Faces> keptRolls;

    // Constructor
    public Player(int numberOfDice) {

        // Initialize variables
        this.points = 0;
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
        currentRoll.clear();

        // Roll all dices and store their value
        for (Dice currentDie : dices) {
            currentRoll.add(currentDie.roll());
        }
    }

    public void keepRandomDice() {
        Random random = new Random();
        // First, check how many dice the player rolled (as we dont want to choose to take something like 5 dice when we only have 3)

    }

    // Getters
    public ArrayList<Faces> getCurrentRoll() {
        return this.currentRoll;
    }
    public int getPoints() {
        return this.points;
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
