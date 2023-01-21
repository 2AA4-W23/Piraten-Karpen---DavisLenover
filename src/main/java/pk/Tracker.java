package pk;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class Tracker {

    public static void printStatsToConsole(Game game) {
        System.out.println("|--------Game Statistics--------|");
        System.out.println("|-----General-----|");
        System.out.println("Rounds Played: " + game.getNumberOfRounds());
        System.out.println("|-----Players-----|");
        for (Player currentPlayer : game.getPlayerList()) {
            System.out.println("|---" + currentPlayer.getPlayerName() + "---|");
            System.out.println("Wins: " + currentPlayer.getWins());
            System.out.printf("Win percentage: %.2f%%\n",((double) currentPlayer.getWins()/(double) game.getNumberOfRounds())*100);
        }
        System.out.println("|--------End Statistics--------|");
    }

}
