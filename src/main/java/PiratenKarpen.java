import pk.Game;
import pk.Player;
import pk.Tracker;

public class PiratenKarpen {

    public static void main(String[] args) {
        System.out.println("Welcome to Piraten Karpen Simulator!");

        System.out.println("Creating two players each with eight dice...");
        Player player1 = new Player("Bob",8);
        Player player2 = new Player("Jim",8);

        Game game = new Game(42, 6000, player1,player2);
        game.playGame();
        Tracker.printStatsToConsole(game);

        System.out.println("That's all folks!");
    }
    
}
