package pk;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Game {

    // Declarations
    Logger classLogger = LogManager.getLogger(Game.class);

    private final ArrayList<Player> playerList;
    private final int numberOfGames;
    private int currentGameNumber;
    private final int winCondition;

    private final CardDeck deck;

    public Game(int numberOfGames, int winCondition, List<Player> players, CardDeck deck) throws NullPlayersException {

        if (players.isEmpty()) {
            throw new NullPlayersException("Player list is empty. Was there any command line arguments?");
        }

        // Variable setup
        this.numberOfGames = numberOfGames;
        this.currentGameNumber = 1;
        this.winCondition = winCondition;
        this.deck = deck;

        // Add players to game
        this.playerList = new ArrayList<>();
        for (Player currentPlayer : players) {
            DevTools.logMessage(this.classLogger,"Found player: " + currentPlayer.getPlayerName(),Level.DEBUG);
            this.playerList.add(currentPlayer);
        }

    }

    // Method to play game
    public void playGame() {

        System.out.println("Simulating " + this.numberOfGames + " of games...");

        while (this.currentGameNumber != this.numberOfGames + 1) {

            DevTools.logMessage(this.classLogger,"", Level.DEBUG);
            DevTools.logMessage(this.classLogger,"|----------GAME " + this.currentGameNumber + "----------|", Level.DEBUG);

            boolean isGameOver = false;
            boolean isLastRound = false;

            deck.fillDeck();
            deck.shuffleDeck();

            while (!isGameOver) {
                // Each player rolls dice and uses a strategy for keeping dice
                for (Player currentPlayer : this.playerList) {
                    if (!checkIfTurnEnds(currentPlayer)) {
                        DevTools.logMessage(this.classLogger,"", Level.DEBUG);
                        DevTools.logMessage(this.classLogger,"|------" + currentPlayer.getPlayerName() + " turn------|", Level.DEBUG);
                        DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": Current points: " + currentPlayer.getPoints(), Level.DEBUG);

                        currentPlayer.setIsFirstRoll(true);

                        // Draw a card and apply any effects of the card to the player specifically
                        currentPlayer.drawCard(deck);
                        DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": Drew a " + currentPlayer.getCard().getCardType().toString() + " card", Level.DEBUG);
                        currentPlayer.getCard().cardEffect(currentPlayer);

                        // Main player loop, roll dice and perform strategy (given they have not triggered an end turn condition)
                        do {

                            if (!checkIfTurnEnds(currentPlayer)) {
                                DevTools.logMessage(this.classLogger, "", Level.DEBUG);
                                DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": Rolling Dice...", Level.DEBUG);
                                currentPlayer.rollDice();

                                // Check if the player reached an island of skulls condition
                                if (currentPlayer.isFirstRoll() && currentPlayer.getNumberOfSkulls() >= 4) {
                                    DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": Player has rolled enough skulls to go to skull island!", Level.DEBUG);
                                    // Call island of skulls method to enter island of skulls
                                    islandOfSkulls(currentPlayer);
                                } else {
                                    currentPlayer.setIsFirstRoll(false);
                                }

                            } else {
                                break;
                            }

                            if (!checkIfTurnEnds(currentPlayer)) {
                                DevTools.logMessage(this.classLogger, "", Level.DEBUG);
                                DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": Executing strategy...", Level.DEBUG);
                                currentPlayer.strategy();
                            } else {
                                break;
                            }

                        } while (!checkIfTurnEnds(currentPlayer));

                        // Add points and reset player dice at the end of their turn
                        // If statement checks if islandOfSkulls happened (as we want to ignore point calculation)
                        if(!(currentPlayer.isFirstRoll() && currentPlayer.getNumberOfSkulls() >= 4)) {
                            currentPlayer.addPoints(Points.calculatePoints(currentPlayer.getKeptRolls(), currentPlayer.getCard()));
                        }
                        DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": Points after turn: " + currentPlayer.getPoints(), Level.DEBUG);
                        currentPlayer.resetDice();

                        DevTools.logMessage(this.classLogger,"|------" + currentPlayer.getPlayerName() + " end turn------|", Level.DEBUG);
                    }

                    // After the player's turn, check if they set a win condition
                    if (isWinConditionSatisfied(currentPlayer,this.getWinCondition()) && !isLastRound){
                        // If so, last round is true, and we set endTurn for the player to true
                        // Thus they cannot play on the next iteration of this loop
                        DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": Set a win condition! One more turn for rest of players!", Level.DEBUG);
                        isLastRound = true;
                        currentPlayer.endTurn();
                    } else if (isLastRound && playerList.get(playerList.size()-1)==currentPlayer) {
                        isGameOver = true;
                    }
                }
            }

            // After each game, check which players set a win condition
            ArrayList<Player> winConditionedPlayers = getWinConditionPlayers(this.playerList, this.getWinCondition());

            // Assign wins or ties
            if (winConditionedPlayers.size() > 0) {
                boolean multipleWins = winConditionedPlayers.size() > 1;
                for (Player currentPlayer : winConditionedPlayers) {
                    if (multipleWins) {
                        currentPlayer.addTie();
                    } else {
                        currentPlayer.addWin();
                    }
                }

                DevTools.logMessage(this.classLogger,"|---------- END GAME " + this.currentGameNumber + "----------|", Level.DEBUG);
                // Resets
                resetRound();
                increaseRound();

            }

        }

    }

    // Method for island of skulls game mechanic
    private void islandOfSkulls(Player currentPlayer) {

        DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": Playing island of skulls...", Level.DEBUG);
        // Loop to have player keep re-rolling for skulls if possible
        while(true) {
            int numberOfSkullsPrevious = currentPlayer.getNumberOfSkulls();
            currentPlayer.rollDice();
            if (currentPlayer.getNumberOfSkulls() == numberOfSkullsPrevious || currentPlayer.getNumberOfSkulls() == 8) {
                // End player turn to tell main game loop that the player has finished their turn upon exit of this method
                currentPlayer.endTurn();
                break;
            }
        }

        // Calculate points to deduct
        int pointsToDeduct = Points.islandOfSkullsPoints(currentPlayer.getKeptRolls());

        DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": Player has ended their turn in island of skulls", Level.DEBUG);
        DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": keptRolls for skulls are: " + currentPlayer.getKeptRolls(), Level.DEBUG);
        DevTools.logMessage(this.classLogger, currentPlayer.getPlayerName() + ": Every other player: " + pointsToDeduct + "points", Level.DEBUG);

        // Deduct points from every player
        for (Player player : this.playerList) {
            if (player != currentPlayer) {
                player.addPoints(pointsToDeduct);
            }
        }

    }

    // Method to check if a player has ended their turn
    private boolean checkIfTurnEnds(Player player) {

        // Check if the player requested to end their turn
        if (player.isTurnDone()) {
            return true;
        }

        // Check for 3 or more skulls
        if (player.getNumberOfSkulls() >= 3) {
            return true;
        }

        // Check if they have no more dice to roll
        return player.getDices().size() == 0;

    }

    private boolean isWinConditionSatisfied(Player player, int winCondition) {
        return player.getPoints() >= winCondition;
    }

    // Method to calculate all players that won the game
    // More than one player can win if a tie occurs
    private ArrayList<Player> getWinConditionPlayers(ArrayList<Player> players, int winCondition) {

        // Variable declaration
        ArrayList<Player> winningPlayers = new ArrayList<>();

        // Go through list of players
        for (Player currentPlayer : players) {

            // If any satisfy the point win condition, add them to the winningPlayers list
            if (isWinConditionSatisfied(currentPlayer, winCondition)) {
                winningPlayers.add(currentPlayer);
            }
        }

        // Check if more than one player satisfied the win condition
        if (winningPlayers.size() > 1) {

            // Variable declaration
            Player highestScoringPlayer = null;
            ArrayList<Player> tiedPlayers = new ArrayList<>();

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
                // If not, return arraylist with the highest scoring player
                ArrayList<Player> highestPlayer = new ArrayList<>();
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

    private int getWinCondition() {
        return this.winCondition;
    }

    public int getNumberOfGames() {
        return this.numberOfGames;
    }

    public ArrayList<Player> getPlayerList() {
        return this.playerList;
    }

    // Setters
    private void increaseRound() {
        this.currentGameNumber++;
    }

}
