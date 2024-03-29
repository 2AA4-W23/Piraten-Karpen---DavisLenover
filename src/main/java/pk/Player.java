package pk;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

// This is abstract as players can have different strategies
// Any methods implemented are the same for every player
public abstract class Player {

    private final Logger classLogger = LogManager.getLogger(Player.class);

    // Declare variables
    private final String playerName;

    private final int mainNumberOfDice;
    private  ArrayList<Dice> dices;
    private ArrayList<Faces> currentRoll;
    private ArrayList<Faces> keptRolls;

    private Card currentCard;

    private int points;
    private int wins;
    private int ties;

    private boolean isTurnDone;
    public boolean isFirstRoll;

    // Constructor
    public Player(String playerName, int numberOfDice) {

        // Initialize variables
        this.playerName = playerName;
        this.points = 0;
        this.wins = 0;
        this.ties = 0;
        this.isTurnDone = false;
        this.isFirstRoll = false;

        this.mainNumberOfDice = numberOfDice;

        this.currentRoll = new ArrayList<>();
        this.dices = new ArrayList<>();
        this.keptRolls = new ArrayList<>();

        // For loop adding dice to array which holds all dice for a given player
        for (int i = 0; i < numberOfDice; i++) {
            this.dices.add(new Dice());
        }
    }

    // Method to create a list of all players given a hashmap
    // Hashmap contains the player type and an integer for how many players of the given type are to be created
    public static ArrayList<Player> createAllPlayers(HashMap<PlayerType,Integer> playersToBeCreated){

        ArrayList<Player> createdPlayerList = new ArrayList<>();

        for (PlayerType type : playersToBeCreated.keySet()) {
            for (int currentNumber = 1; currentNumber <= playersToBeCreated.get(type); currentNumber++) {
                if (type == PlayerType.RANDOM) {
                    createdPlayerList.add(new PlayerRandom("PlayerRandom"+currentNumber,8));
                } else if (type == PlayerType.COMBO) {
                    createdPlayerList.add(new PlayerCombo("PlayerCombo"+currentNumber,8));
                } else if (type == PlayerType.SEABATTLE) {
                    createdPlayerList.add(new PlayerSeaBattle("PlayerSeaBattle"+currentNumber,8));
                }
            }
        }

        return createdPlayerList;
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

        DevTools.logMessage(this.classLogger,this.getPlayerName() + ": Rolled " + this.getCurrentRoll(), Level.DEBUG);

        // Then add skulls to kept dice
        addSkulls();

    }

    // Method to add all skulls rolled to keptRolls
    private void addSkulls() {

        // Loop through all dice rolled
        for (Faces currentFace : this.currentRoll) {
            // Check if any were a skull
            if (currentFace.equals(Faces.SKULL)) {
                // If so, add it to kept rolls and remove one die from the player
                this.keptRolls.add(currentFace);
                this.dices.remove(0);
            }
        }

        // Remove any skulls from current roll array list
        this.currentRoll.removeIf(face -> face.equals(Faces.SKULL));

        DevTools.logMessage(this.classLogger,this.getPlayerName() + ": Attempted to remove any skulls, currentRoll is now: " + this.getCurrentRoll(), Level.DEBUG);
        DevTools.logMessage(this.classLogger,this.getPlayerName() + ": keptRolls is now: " + this.getKeptRolls(), Level.DEBUG);
    }

    // Method to keep the first instance of a roll
    // Note that there is no real reason to deal with keep a specific position of two similar roles as they are identical
    public void keepRoll(Faces rollToKeep) {

        // Check if current roll contains the Face
        if (this.getCurrentRoll().contains(rollToKeep)) {

            int currentRollIndex = 0;

            for (Faces currentRoll : this.getCurrentRoll()) {

                if (currentRoll == rollToKeep) {
                    // If it does, add it to the kept rolls
                    this.getKeptRolls().add(currentRoll);
                    break;
                }
                currentRollIndex++;
            }

            // Remove the roll from the currentRoll array list afterwards
            this.getCurrentRoll().remove(currentRollIndex);
            this.getDices().remove(0);
        }

    }

    // Method to keep all instances of a given roll
    public void keepAllRolls(Faces rollToKeep) {

        while (this.getCurrentRoll().contains(rollToKeep)) {
            int currentRollIndex = 0;

            // Check if the requested dice to remove even exists
            for (Faces currentRoll : this.getCurrentRoll()) {

                if (currentRoll == rollToKeep) {
                    // If it does, add it to the kept rolls
                    this.getKeptRolls().add(currentRoll);
                    break;
                }
                currentRollIndex++;
            }

            // Remove the roll from the currentRoll array list afterwards
            this.getCurrentRoll().remove(currentRollIndex);
            this.getDices().remove(0);
        }

    }

    public HashMap<Faces,Integer> checkCombinations(ArrayList<Faces> rolls) {
        // Setup HashMap to keep track of all face duplicates in the roll
        HashMap<Faces,Integer> setLog = new HashMap<>();

        for (Faces currentFace : Faces.values()) {
            setLog.put(currentFace,0);
        }

        // Count all faces
        for (Faces currentRoll : rolls) {
            // Add 1 to the count of that specific face
            setLog.put(currentRoll,setLog.get(currentRoll) + 1);
        }

        return setLog;
    }

    // Method to draw a card from a given card deck
    public void drawCard(CardDeck deck) {
        this.currentCard = deck.drawCard();
    }

    public Card getCard() {
        return this.currentCard;
    }

    // Method for player strategy
    // This is a blueprint for specific extensions of player to implement
    public abstract void strategy();

    public void resetDice() {
       this.currentRoll.clear();
       this.keptRolls.clear();
       this.dices.clear();
        for (int i = 0; i < this.mainNumberOfDice; i++) {
            this.dices.add(new Dice());
        }
        this.isTurnDone = false;
    }

    public boolean isFirstRoll() {
        return isFirstRoll;
    }

    // Getters
    public String getPlayerName() {
        return this.playerName;
    }

    public int getWins() {
        return this.wins;
    }

    public int getTies() {
        return this.ties;
    }

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

    public boolean isTurnDone() {
        return isTurnDone;
    }

    // Setters
    public void setPoints(int points) {
        this.points = points;
    }
    public void addPoints(int points) {
        this.points += points;
    }

    public void addWin() {
        this.wins += 1;
    }

    public void addTie() {
        this.ties += 1;
    }

    public void endTurn() {
        this.isTurnDone = true;
    }

    public void setIsFirstRoll(boolean set) {
        this.isFirstRoll = set;
    }

}
