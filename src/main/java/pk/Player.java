package pk;

public class Player {

    // Declare variables
    private Dice[] dices;
    private int points;
    private Faces[] currentRoll;
    private Faces[] keptRolls;

    // Constructor
    public Player(int numberOfDice) {

        // Initialize variables
        this.points = 0;
        this.currentRoll = new Faces[numberOfDice];
        // Create number of dice needed
        this.dices = new Dice[numberOfDice];
        // For loop adding dice to array which holds all dice for a given player
        for (int i = 0; i < numberOfDice; i++) {
            this.dices[i] = new Dice();
        }
    }

    // Method to roll all dice the player has
    // What the player rolled is then stored in the currentRoll array
    public void rollDice() {
        for (int i = 0; i < this.dices.length; i++) {
            // Check if any specific dice was kept during the player's turn
            // i.e., it will not be rolled
            if (keptRolls[i] == null) {
                this.currentRoll[i] = this.dices[i].roll();
            }
        }
    }

    // Getters
    public Faces[] getCurrentRoll() {
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
