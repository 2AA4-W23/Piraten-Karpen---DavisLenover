package pk;

import java.util.ArrayList;

public class MainGame {

    // Method to play game
    public static void playGame(int numOfGames, Player ... players) {

        // Determine number of players
        ArrayList<Player> playerList = new ArrayList<Player>();
        for (Player player : players) {
            playerList.add(player);
        }

        // Stats variable declarations
        int currentGamenum = 0;

        System.out.println("Let the game begin!");
        System.out.println("Simulating...");

        while (currentGamenum != numOfGames) {

            for (Player currentPlayer : playerList) {
                do {
                    currentPlayer.rollDice();
                    if (currentPlayer.getDices().size() != 0) {
                        currentPlayer.keepRandomDice();
                    } else {
                        break;
                    }
                } while (currentPlayer.getNumberOfSkulls() < 2);

                currentPlayer.addPoints(Points.checkForPoints(currentPlayer.getKeptRolls()));
                currentPlayer.resetDice();
            }

            // After each turn, check if any win condition has been set
            if (player1.getPoints() >= 6000 || player2.getPoints() >= 6000) {
                if (player1.getPoints() > player2.getPoints()) {
                    //System.out.println("(DEBUG) Player 1 wins!");
                    player1Wins++;
                } else if (player1.getPoints() < player2.getPoints()) {
                    //System.out.println("(DEBUG) Player 2 wins!");
                    player2Wins++;
                } else {
                    //System.out.println("(DEBUG) It is a tie!");
                    ties++;
                }

                // Reset
                player1.resetDice();
                player2.resetDice();
                player1.setPoints(0);
                player2.setPoints(0);

                currentGamenum++;

            }
        }

        // End of round statistics
        System.out.println("Simulation complete!");
        System.out.println("Number of games played: " + numOfGames);
        // Player 1
        System.out.printf("Player 1 wins: %d, ",player1Wins);
        System.out.printf("Player 1 %% of wins: %.02f%%\n",(float) player1Wins/numOfGames*100);
        // Player 2
        System.out.printf("Player 2 wins: %d, ",player2Wins);
        System.out.printf("Player 2 %% of wins: %.02f%%\n",(float) player2Wins/numOfGames*100);
        // Ties
        System.out.printf("Ties: %d, ",ties);
        System.out.printf("%% of ties: %.02f%%\n",(float) ties/numOfGames*100);

    }


    public ArrayList<Player> getWinConditionPlayers(ArrayList<Player> players, int winCondition) {

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

        }

    }

}
