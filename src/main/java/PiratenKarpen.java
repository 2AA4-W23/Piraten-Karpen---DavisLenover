import pk.Dice;
import pk.Player;
import pk.PointCalc;

public class PiratenKarpen {

    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");

        System.out.println("Creating two players each with eight dice...");
        Player player1 = new Player(8);
        Player player2 = new Player(8);

        int numOfGames = 42;
        int currentGamenum = 1;

        System.out.println("Let the game begin!");

        while (currentGamenum != numOfGames) {
            System.out.println("Player 1's turn...");
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

            System.out.println("Player 2's turn...");
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

            if (player1.getPoints() >= 6000) {

            } else if (player2.getPoints() >= 6000) {

            }

        }

        System.out.println("Rolling Dice for Player1...");
        player1.rollDice();
        System.out.println(player1.getCurrentRoll());
        System.out.println("");

        System.out.println("Rolling Dice for Player2...");
        player2.rollDice();
        System.out.println(player2.getCurrentRoll());
        System.out.println("");

        System.out.println("That's all folks!");
    }
    
}
