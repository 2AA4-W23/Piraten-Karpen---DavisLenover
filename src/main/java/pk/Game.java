package pk;

import java.util.ArrayList;

public class Game {

    private ArrayList<Player> playerList;
    private int numberOfRounds;
    private int currentRoundNumber;
    private int winCondition;

    public Game(int numberOfRounds, int winCondition, Player ... players) {
        this.playerList = new ArrayList<Player>();
        this.numberOfRounds = numberOfRounds;
        this.currentRoundNumber = 0;
        this.winCondition = winCondition;
        for (Player currentPlayer : players) {
            this.playerList.add(currentPlayer);
        }
    }

    // Method to play game
    public void playGame() {

        // Stats variable declarations
        this.currentRoundNumber = 1;

        while (this.currentRoundNumber != this.numberOfRounds) {

            // Each player rolls dice and uses a strategy for keeping dice
            for (Player currentPlayer : this.playerList) {
                do {
                    currentPlayer.rollDice();
                    if (currentPlayer.getDices().size() != 0) {
                        currentPlayer.strategy();
                    } else {
                        break;
                    }
                } while (currentPlayer.getNumberOfSkulls() < 2);

                // Add points and reset player dice at the end of their turn
                currentPlayer.addPoints(Points.checkForPoints(currentPlayer.getKeptRolls()));
                currentPlayer.resetDice();
            }

            // After each turn, check if any win condition has been set
            ArrayList<Player> winConditionedPlayers = Game.getWinConditionPlayers(this.playerList, this.getWinCondition());

            if (winConditionedPlayers.size() > 0) {
                for (Player currentPlayer : winConditionedPlayers) {
                    currentPlayer.addWin();
                }

                // Resets
                resetRound();
                increaseRound();

            }

        }

    }


    public static ArrayList<Player> getWinConditionPlayers(ArrayList<Player> players, int winCondition) {

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

    public void resetRound() {
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
    public void increaseRound() {
        this.currentRoundNumber++;
    }

}
