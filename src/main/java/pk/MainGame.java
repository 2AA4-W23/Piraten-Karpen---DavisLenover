package pk;

public class MainGame {

    // Method to play game
    public static void playGame(Player player1, Player player2) {

        // Stats variable declarations
        int numOfGames = 42;
        int currentGamenum = 0;

        int player1Wins = 0;
        int player2Wins = 0;
        int ties  = 0;

        System.out.println("Let the game begin!");
        System.out.println("Simulating...");

        while (currentGamenum != numOfGames) {

            // Player rolling and keeping random dice
            //System.out.println("(DEBUG) Player 1's turn...");
            do {
                player1.rollDice();
                if (player1.getDices().size() != 0) {
                    player1.keepRandomDice();
                } else {
                    break;
                }
            } while (player1.getNumberOfSkulls() < 2);

            player1.addPoints(PointCalc.checkForPoints(player1.getKeptRolls()));
            player1.resetDice();

            //System.out.println("(DEBUG) Player 2's turn...");
            do {
                player2.rollDice();
                if (player2.getDices().size() != 0) {
                    player2.keepRandomDice();
                } else {
                    break;
                }
            } while (player2.getNumberOfSkulls() < 2);

            player2.addPoints(PointCalc.checkForPoints(player2.getKeptRolls()));
            player2.resetDice();

            // After each turn, check if any win condition has been set
            if (player1.getPoints() >= 6000 || player2.getPoints() >= 6000) {
                if (player1.getPoints() > player2.getPoints()) {
                    //System.out.println("(DEBUG) Player 1 wins!");
                    player1Wins++;
                    player1.resetDice();
                    player2.resetDice();
                    player1.setPoints(0);
                    player2.setPoints(0);
                } else if (player1.getPoints() < player2.getPoints()) {
                    //System.out.println("(DEBUG) Player 2 wins!");
                    player2Wins++;
                    player1.resetDice();
                    player2.resetDice();
                    player1.setPoints(0);
                    player2.setPoints(0);
                } else {
                    //System.out.println("(DEBUG) It is a tie!");
                    ties++;
                    player1.resetDice();
                    player2.resetDice();
                    player1.setPoints(0);
                    player2.setPoints(0);
                }
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

}
