package pk;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// The purpose of this class is to house all the necessary methods for point calculation
public class Points {

    private static Logger classLogger = LogManager.getLogger(Points.class);

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
    public static int calculatePoints(ArrayList<Faces> rollsToCheck, Card card) {

        DevTools.logMessage(classLogger,"", Level.DEBUG);
        DevTools.logMessage(classLogger,"Checking roll set for points: " + rollsToCheck, Level.DEBUG);

        int awardedPoints = 0;

        int keptRollsSize = rollsToCheck.size();

        // Add corresponding points for card effects
        awardedPoints += checkCardEffects(rollsToCheck,card);

        if (awardedPoints < 0) {
            DevTools.logMessage(classLogger,"Roll has failed the card check. Total points: " + awardedPoints, Level.DEBUG);
            return awardedPoints;
        }


        // First check if the roll set has 3 skulls
        if (keptRollsSize >= 3) {
            if (has3Skulls(rollsToCheck)) {
                DevTools.logMessage(classLogger,"Roll contains 3 or more skulls, no points awarded", Level.DEBUG);
                // If so award 0 points
                return 0;
            }
        }

        // To calculate if the player obtained a full chest, use a boolean array which each index corresponds to the given rolls
        boolean[] rollsUsed = new boolean[keptRollsSize];
        Arrays.fill(rollsUsed, false);

        // Calculate points and set which rolls were used accordingly
        awardedPoints += checkForDiamondAndGold(rollsToCheck, rollsUsed);
        awardedPoints += checkForSets(rollsToCheck, rollsUsed);

        // Check for Full Chest
        if (rollsUsed.length == 8) {
            boolean fullChest = true;
            // Check if all dice were used (must be 8 too!)
            for (boolean currentBool : rollsUsed) {
                if (currentBool == false) {
                    // If not subtract the 500 points awarded
                    fullChest = false;
                    break;
                }
            }

            if (fullChest) {
                DevTools.logMessage(classLogger,"All dice were used in calculation. Thus a full chest occurred, +500 points", Level.DEBUG);
                awardedPoints += 500;
            }

        }

        DevTools.logMessage(classLogger,"Total awarded points: " + awardedPoints, Level.DEBUG);
        return awardedPoints;

    }

    // Method to calculate points awarded for a given card
    private static int checkCardEffects(ArrayList<Faces> rollsToCheck, Card card) {

        DevTools.logMessage(classLogger,"Checking drawn card...", Level.DEBUG);

        if (card.getCardType() == FortuneCards.SEA_BATTLE) {

            SeaBattleCard seaBattleCard = (SeaBattleCard) card;

            int sabersNeeded = seaBattleCard.getNumberOfSabers();
            int numberOfSabersInRoll = 0;

            DevTools.logMessage(classLogger,"Checking if roll has " + sabersNeeded + " " + Faces.SABER.toString(), Level.DEBUG);

            for (Faces currentFace : rollsToCheck) {
                if (currentFace == Faces.SABER) {
                    numberOfSabersInRoll++;
                }
            }

            if (numberOfSabersInRoll >= sabersNeeded) {

                int pointsToAward = seaBattleCard.getBonusPoints();

                DevTools.logMessage(classLogger,"Roll has " + sabersNeeded + " " + Faces.SABER.toString(), Level.DEBUG);
                DevTools.logMessage(classLogger,"+" + pointsToAward + " points", Level.DEBUG);

                return pointsToAward;

            } else {

                int pointsToAward = seaBattleCard.getBonusPoints()*(-1);

                DevTools.logMessage(classLogger,"Roll does not have " + sabersNeeded + " or more " + Faces.SABER.toString(), Level.DEBUG);
                DevTools.logMessage(classLogger,"-" + pointsToAward + " points", Level.DEBUG);

                return pointsToAward;
            }

        } else {
            DevTools.logMessage(classLogger,"Drawn card is null", Level.DEBUG);
            return 0;
        }

    }

    // Method to check if 3 skulls are in the current roll
    private static boolean has3Skulls(ArrayList<Faces> rollsToCheck) {
        int numberOfSkulls = 0;

        for (Faces currentFace : rollsToCheck) {
            if (currentFace == Faces.SKULL) {
                numberOfSkulls++;
            }
        }

        if (numberOfSkulls >= 3) {
            return true;
        }

        return false;

    }

    // Method to calculate points for a player given the amount of gold and diamond coins
    private static int checkForDiamondAndGold(ArrayList<Faces> rollToCheck, boolean[] rollsUsed) {

        DevTools.logMessage(classLogger,"Checking roll set for diamond and gold rolls...", Level.DEBUG);

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

        int points = (numOfDiamonds + numOfGolds)*100;

        DevTools.logMessage(classLogger,"Found " + numOfDiamonds + " Diamond(s) and " + numOfGolds + " Gold(s): " + "+" + points + " points", Level.DEBUG);

        // Return points awarded
        return points;
    }

    // Method to check roll for sets and award points based off any given set
    private static int checkForSets(ArrayList<Faces> rollToCheck, boolean[] rollsUsed) {

        DevTools.logMessage(classLogger,"Checking roll set for sets...", Level.DEBUG);

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
        for (Faces currentFace : setLog.keySet()) {
            if (currentFace != Faces.SKULL) {
                int numberOfDuplicates = setLog.get(currentFace);
                if (numberOfDuplicates >= 3) {
                    int awardPoints = pointList[numberOfDuplicates-1];
                    DevTools.logMessage(classLogger,currentFace.toString() + ":" + numberOfDuplicates + ":" + "+" + awardPoints + " points", Level.DEBUG);
                    totalPoints += awardPoints;
                }
            }
        }

        // After point calculation, check which dice were used
        for (Faces currentFace : setLog.keySet()) {

            // Check if any duplicates of dice were 3 or more
            // This must mean that Face made some set
            if (setLog.get(currentFace) >= 3 && currentFace != Faces.SKULL) {

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


    // Method to remove any rolls to exclude from set point calculation
    private static ArrayList<Faces> removeAllRolls(ArrayList<Faces> rollSet, Faces rollTypeToRemove) {

        while (rollSet.contains(rollTypeToRemove)) {
            int currentRollIndex = 0;

            // Check if the requested dice to remove even exists
            for (Faces currentRoll : rollSet) {

                if (currentRoll == rollTypeToRemove) {
                    // If it does, we have the index value to remove so break
                    break;
                }
                currentRollIndex++;
            }

            // Remove the roll from the currentRoll array list afterwards
            rollSet.remove(currentRollIndex);
        }

        return rollSet;

    }


}
