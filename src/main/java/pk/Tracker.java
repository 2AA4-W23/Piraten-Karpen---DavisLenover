package pk;

public class Tracker {

    public static void printStatsToConsole(Game game) {
        System.out.println("|--------Game Statistics--------|");
        System.out.println("|-----General-----|");
        System.out.println("Games Played: " + game.getNumberOfGames());
        System.out.println("|-----Players-----|");
        for (Player currentPlayer : game.getPlayerList()) {
            System.out.println("|---" + currentPlayer.getPlayerName() + "---|");
            System.out.println("Wins: " + currentPlayer.getWins());
            System.out.println("Ties: " + currentPlayer.getTies());
            System.out.printf("Win percentage: %.2f%%\n",((double) currentPlayer.getWins()/(double) game.getNumberOfGames())*100);
            System.out.printf("Tie percentage: %.2f%%\n",((double) currentPlayer.getTies()/(double) game.getNumberOfGames())*100);
        }
        System.out.println("|--------End Statistics--------|");
    }

}
