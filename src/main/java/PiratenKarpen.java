import pk.Dice;
import pk.Player;

public class PiratenKarpen {

    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");

        System.out.println("Creating two players each with eight dice...");
        Player player1 = new Player(8);
        Player player2 = new Player(8);

        System.out.println("Rolling Dice for Player1...");
        player1.rollDice();
        for (int i = 0; i < 8; i++) {
            System.out.print(player1.getCurrentRoll()[i] + " ");
        }
        System.out.println("");

        System.out.println("Rolling Dice for Player2...");
        player2.rollDice();
        for (int i = 0; i < 8; i++) {
            System.out.print(player2.getCurrentRoll()[i] + " ");
        }
        System.out.println("");

        System.out.println("That's all folks!");
    }
    
}
