package pk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

// The purpose of this class is to house all the necessary methods for point calculation
public class Points {

    Logger classLogger = LogManager.getLogger(Points.class);

    /*There are three ways to score points:

            1. Sets of identical objects: Each set of at least three identical objects rewards points as per the following:
            3 of a kind: 100 points; 4 of a kind: 200 points; 5 of a kind: 500 points;
            6 of a kind: 1,000 points; 7 of a kind: 2,000 points;
            8 of a kind: 4,000 points.

            2. Diamonds and Gold: Each diamond and each gold coin is worth 100 points (even if not part of a set).
            Therefore, sets of diamonds and gold coins score twice: First for their face value and then for the sets they make.

            3. Full Chest: A player who generates points with all eight dice re-ceives a bonus of 500 points
            in addition to the score they made. */

    // Method to calculate points for a player given the amount of gold and diamond coins
    public static int checkForPoints(ArrayList<Faces> rollToCheck) {

        // Initialize
        int numOfDiamonds = 0;
        int numOfGolds = 0;

        // Loop through all dice and find how many are gold/diamond
        for (Faces currentFace : rollToCheck) {
            if (currentFace == Faces.DIAMOND) {
                numOfDiamonds++;
            } else if (currentFace == Faces.GOLD) {
                numOfGolds++;
            }
        }
        // Return points awarded
        return (numOfDiamonds + numOfGolds)*100;
    }

    private static int checkForSets(ArrayList<Faces> rollToCheck) {

        // Point list corresponding to x (x = numberOfDuplicates-1) of a kind dice
        int[] pointList = {0,0,100,200,500,1000,2000,4000};

        int totalPoints = 0;

        // Setup HashMap to keep track of all face duplicates in the roll
        HashMap<Faces,Integer> setLog = new HashMap<Faces,Integer>();
        for (Faces currentFace : Faces.values()) {
            setLog.put(currentFace,0);
        }

        // Count all faces
        for (Faces currentRoll : rollToCheck) {
            // Add 1 to the count of that specific face
            setLog.put(currentRoll,setLog.get(currentRoll) + 1);
        }

        // Award points
        for (int numberOfDuplicates : setLog.values()) {
            totalPoints += pointList[numberOfDuplicates-1];
        }

        return totalPoints;

    }

}
