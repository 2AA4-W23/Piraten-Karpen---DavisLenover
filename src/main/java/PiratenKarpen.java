import pk.MainGame;
import pk.Player;

public class PiratenKarpen {

    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");

        System.out.println("Creating two players each with eight dice...");
        Player player1 = new Player(8);
        Player player2 = new Player(8);

        // Play game
        MainGame.playGame(player1, player2, 42);

        System.out.println("That's all folks!");
    }
    
}
