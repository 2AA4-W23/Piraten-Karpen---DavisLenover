package pk;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
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

    // Method to calculate how many points a given set of rolls is currently worth
    public static int calculatePoints(ArrayList<Faces> rollsToCheck) {

        int awardedPoints = 0;

        int keptRollsSize = rollsToCheck.size();

        // To calculate if the player obtained a full chest, use a boolean array which each index corresponds to the given rolls
        boolean[] rollsUsed = new boolean[keptRollsSize];
        Arrays.fill(rollsUsed, false);

        // Calculate points and set which rolls were used accordingly
        awardedPoints += checkForDiamondAndGold(rollsToCheck, rollsUsed);
        awardedPoints += checkForSets(rollsToCheck, rollsUsed);
        // Check for Full Chest
        if (rollsUsed.length == 8) {
            awardedPoints += 500;
            // Check if all dice were used (must be 8 too!)
            for (boolean currentBool : rollsUsed) {
                if (currentBool == false) {
                    // If not subtract the 500 points awarded
                    awardedPoints -= 500;
                    break;
                }
            }
        }


        return awardedPoints;

    }

    // Method to calculate points for a player given the amount of gold and diamond coins
    private static int checkForDiamondAndGold(ArrayList<Faces> rollToCheck, boolean[] rollsUsed) {

        // Initialize
        int numOfDiamonds = 0;
        int numOfGolds = 0;

        int diceUsedIndex = 0;

        // Loop through all dice and find how many are gold/diamond
        for (Faces currentFace : rollToCheck) {
            if (currentFace == Faces.DIAMOND) {
                // Set said roll in the rollsUsed index to true
                rollsUsed[diceUsedIndex] = true;
                numOfDiamonds++;
            } else if (currentFace == Faces.GOLD) {
                numOfGolds++;
                rollsUsed[diceUsedIndex] = true;
            }

            diceUsedIndex++;

        }
        // Return points awarded
        return (numOfDiamonds + numOfGolds)*100;
    }

    private static int checkForSets(ArrayList<Faces> rollToCheck, boolean[] rollsUsed) {

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
            if (numberOfDuplicates >= 3) {
                totalPoints += pointList[numberOfDuplicates-1];
            }
        }

        // After point calculation, check which dice were used
        for (Faces currentFace : setLog.keySet()) {

            // Check if any duplicates of dice were 3 or more
            // This must mean that Face made some set
            if (setLog.get(currentFace) >= 3) {

                // Loop through the rollToCheck and find the corresponding roll positions to set to true in diceUsed
                int diceUsedIndex = 0;

                for (Faces checkFace : rollToCheck) {
                    if (currentFace == checkFace) {
                        rollsUsed[diceUsedIndex] = true;
                    }

                    diceUsedIndex++;

                }
            }
        }

        return totalPoints;

    }

}
