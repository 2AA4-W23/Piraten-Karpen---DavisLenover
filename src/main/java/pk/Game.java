package pk;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Game {

    Logger classLogger = LogManager.getLogger(Game.class);

    private ArrayList<Player> playerList;
    private int numberOfRounds;
    private int currentRoundNumber;
    private int winCondition;

    private CardDeck deck;

    public Game(int numberOfRounds, int winCondition, List<Player> players, CardDeck deck) throws NullPlayersException {

        if (players.isEmpty()) {
            throw new NullPlayersException("Player list is empty. Was there any command line arguments?");
        }

        this.playerList = new ArrayList<Player>();
        this.numberOfRounds = numberOfRounds;
        this.currentRoundNumber = 0;
        this.winCondition = winCondition;
        this.deck = deck;
        for (Player currentPlayer : players) {
            DevTools.logMessage(this.classLogger,"Found player: " + currentPlayer.getPlayerName(),Level.DEBUG);
            this.playerList.add(currentPlayer);
        }

    }

    // Method to play game
    public void playGame() {

        // Stats variable declarations
        this.currentRoundNumber = 1;

        while (this.currentRoundNumber != this.numberOfRounds + 1) {

            DevTools.logMessage(this.classLogger,"", Level.DEBUG);
            DevTools.logMessage(this.classLogger,"|----------ROUND " + this.currentRoundNumber + "----------|", Level.DEBUG);

            int lastRoundTimer = 2;
            boolean isLastRound = false;

            deck.shuffleDeck();

            while (lastRoundTimer != 0) {

                // Each player rolls dice and uses a strategy for keeping dice
                for (Player currentPlayer : this.playerList) {
                    if (!checkIfTurnEnds(currentPlayer)) {
                        DevTools.logMessage(this.classLogger,"", Level.DEBUG);
                        DevTools.logMessage(this.classLogger,"|------" + currentPlayer.getPlayerName() + " turn------|", Level.DEBUG);
                        DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": Current points: " + currentPlayer.getPoints(), Level.DEBUG);

                        // Draw a card and apply any effects of the card to the player specifically
                        currentPlayer.drawCard(deck);
                        DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": Drew a " + currentPlayer.getCard().getCardType().toString() + " card", Level.DEBUG);
                        currentPlayer.getCard().cardEffect(currentPlayer);
                        do {
                            if (currentPlayer.getDices().size() != 0 && !checkIfTurnEnds(currentPlayer)) {
                                DevTools.logMessage(this.classLogger, "", Level.DEBUG);
                                DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": Rolling Dice...", Level.DEBUG);
                                currentPlayer.rollDice();
                            }
                            if (!checkIfTurnEnds(currentPlayer)) {
                                DevTools.logMessage(this.classLogger, "", Level.DEBUG);
                                DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": Executing strategy...", Level.DEBUG);
                                currentPlayer.strategy();
                            } else {
                                break;
                            }
                        } while (currentPlayer.getNumberOfSkulls() <= 2 && currentPlayer.getKeptRolls().size() < 8);

                        // Add points and reset player dice at the end of their turn
                        currentPlayer.addPoints(Points.calculatePoints(currentPlayer.getKeptRolls(), currentPlayer.getCard()));
                        DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": Points after turn: " + currentPlayer.getPoints(), Level.DEBUG);
                        currentPlayer.resetDice();

                        // After the player's turn, check if they set a win condition
                        if (isWinConditionSatisfied(currentPlayer,this.getWinCondition()) && isLastRound != true){
                            // If so, last round is true and we set endTurn for the player to true
                            // Thus they cannot play on the next iteration of this loop
                            DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": Set a win condition! One more turn for rest of players!", Level.DEBUG);
                            isLastRound = true;
                            currentPlayer.endTurn();
                            break;
                        }
                        DevTools.logMessage(this.classLogger,"|------" + currentPlayer.getPlayerName() + " end turn------|", Level.DEBUG);
                    }

                }

                // Keep decreasing by 1
                // The while loop after one iteration will be false and thus, the game (current game) will end
                if (isLastRound) {
                    lastRoundTimer--;
                }

            }

            // After each game, check which players set a win condition
            ArrayList<Player> winConditionedPlayers = getWinConditionPlayers(this.playerList, this.getWinCondition());

            // Assign wins or ties
            if (winConditionedPlayers.size() > 0) {
                boolean multipleWins = false;
                if (winConditionedPlayers.size() > 1) {
                    multipleWins = true;
                }
                for (Player currentPlayer : winConditionedPlayers) {
                    if (multipleWins) {
                        currentPlayer.addTie();
                    } else {
                        currentPlayer.addWin();
                    }
                }

                DevTools.logMessage(this.classLogger,"|---------- END ROUND " + this.currentRoundNumber + "----------|", Level.DEBUG);
                // Resets
                resetRound();
                increaseRound();

            }

        }

    }

    // Method to check if a player has three skulls or they would like to end their turn
    private boolean checkIfTurnEnds(Player player) {

        if (player.isTurnDone()) {
            return true;
        }

        int numberOfSkulls = 0;
        for (Faces face : player.getKeptRolls()) {
            if (face.equals(Faces.SKULL)) {
                numberOfSkulls++;
            }
        }

        if (numberOfSkulls == 3) {
            return true;
        }

        return false;

    }

    private boolean isWinConditionSatisfied(Player player, int winCondition) {
        if (player.getPoints() >= winCondition) {
            return true;
        } else {
            return false;
        }
    }


    private ArrayList<Player> getWinConditionPlayers(ArrayList<Player> players, int winCondition) {

        // Variable declaration
        ArrayList<Player> winningPlayers = new ArrayList<Player>();

        // Go through list of players
        for (Player currentPlayer : players) {

            // If any satisfy the point win condition, add them to the winningPlayers list
            if (currentPlayer.getPoints() >= winCondition) {
                winningPlayers.add(currentPlayer);
            }
        }

        // Check if more than one player satisfied the win condition
        if (winningPlayers.size() > 1) {

            // Variable declaration
            Player highestScoringPlayer = null;
            ArrayList<Player> tiedPlayers = new ArrayList<Player>();

            // Go through all winning players and check who has higher points or tied
            for (Player currentPlayer : winningPlayers) {
                if (highestScoringPlayer == null) {
                    highestScoringPlayer = currentPlayer;
                    continue;
                }

                if (highestScoringPlayer.getPoints() < currentPlayer.getPoints()) {
                    highestScoringPlayer = currentPlayer;
                    tiedPlayers.clear();

                } else if (highestScoringPlayer.getPoints() == currentPlayer.getPoints()) {
                    tiedPlayers.add(currentPlayer);
                    if (!tiedPlayers.contains(highestScoringPlayer)) {
                        tiedPlayers.add(highestScoringPlayer);
                    }
                }

            }

            // At the end of the for loop, check if two or more players are tied
            if (tiedPlayers.size() > 1){
                return tiedPlayers;
            } else {
                // If not, return arraylist with highest scoring player
                ArrayList<Player> highestPlayer = new ArrayList<Player>();
                highestPlayer.add(highestScoringPlayer);
                return highestPlayer;
            }

        } else {
            return winningPlayers;
        }

    }

    private void resetRound() {
        for (Player currentPlayer : this.playerList) {
            currentPlayer.resetDice();
            currentPlayer.setPoints(0);
        }
    }

    // Getters
    public ArrayList<Player> getPlayerList() {
        return this.playerList;
    }

    public int getWinCondition() {
        return this.winCondition;
    }

    public int getNumberOfRounds() {
        return this.numberOfRounds;
    }

    // Setters
    private void increaseRound() {
        this.currentRoundNumber++;
    }

}
