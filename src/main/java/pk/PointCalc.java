package pk;

import java.util.ArrayList;

// The purpose of this class is to house all the necessary methods for point calculation
public class PointCalc {

    // Method to calculate points for a player given the amount of gold and diamond coins
    public static int checkForPoints(ArrayList<Faces> diceToCheck) {

        // Initialize
        int numOfDiamonds = 0;
        int numOfGolds = 0;

        // Loop through all dice and find how many are gold/diamond
        for (Faces currentFace : diceToCheck) {
            if (currentFace == Faces.DIAMOND) {
                numOfDiamonds++;
            } else if (currentFace == Faces.GOLD) {
                numOfGolds++;
            }
        }

        // Return points awarded
        return (numOfDiamonds + numOfGolds)*100;
    }

}
