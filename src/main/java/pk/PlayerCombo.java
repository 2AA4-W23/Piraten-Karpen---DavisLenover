package pk;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerCombo extends Player {

    Logger classLogger = LogManager.getLogger(PlayerCombo.class);

    public PlayerCombo(String playerName, int numberOfDice) {
        super(playerName, numberOfDice);
    }

    public void strategy() {

        ArrayList<Faces> currentRoll = super.getCurrentRoll();
        ArrayList<Faces> keptRolls = super.getKeptRolls();

        DevTools.logMessage(this.classLogger,super.getPlayerName() + ": Checking for sets in: " + currentRoll.toString() + " given kept rolls of: " + keptRolls.toString(), Level.DEBUG);

        // Player will re-roll until at least one set of dice is made (2 or greater)
        // Player will keep these dice and re-roll, looking to maximize said combination
        // If a different set of dice appear, evaluate if it is possible to keep said set
        HashMap<Faces,Integer> keptCombinations = checkCombinations(keptRolls);
        HashMap<Faces,Integer> rolledCombinations = checkCombinations(currentRoll);

        // Skip skulls as they are by default added to the player's kept rolls

        if (keptCombinations.isEmpty()) {
            DevTools.logMessage(this.classLogger,super.getPlayerName() + ": Kept rolls are empty, checking for sets...", Level.DEBUG);
            for (Faces currentFace : Faces.values()) {
                int faceCount = rolledCombinations.get(currentFace);
                if (faceCount >= 2 && currentFace != Faces.SKULL && super.getKeptRolls().size() < 6) {
                    DevTools.logMessage(this.classLogger,super.getPlayerName() + ": Found set of " + faceCount + " " + currentFace.toString() + " to keep", Level.DEBUG);
                    super.keepAllRolls(currentFace);
                }
            }
        } else {
            for (Faces currentFace : Faces.values()) {
                /* A combo player currently maximizes combos in two ways
                1. Check if they kept at least two of the given roll
                2. If a new combination appears that is at least a set of 2 and there is at least one extra space after adding to the kept rolls
                 */
                int faceCount = rolledCombinations.get(currentFace);
                if (faceCount >= 1 && keptCombinations.get(currentFace) >= 2 && currentFace != Faces.SKULL) {
                    DevTools.logMessage(this.classLogger,super.getPlayerName() + ": Found set of another " + faceCount + " " + currentFace.toString() + " to keep", Level.DEBUG);
                    super.keepAllRolls(currentFace);
                } else if (faceCount >= 2 && keptCombinations.get(currentFace) >= 0 && super.getKeptRolls().size() < 8 - rolledCombinations.get(currentFace) && currentFace != Faces.SKULL) {
                    DevTools.logMessage(this.classLogger,super.getPlayerName() + ": A new set of " + faceCount + " " + currentFace.toString() + " appeared to keep", Level.DEBUG);
                    super.keepAllRolls(currentFace);
                }
            }
        }

        DevTools.logMessage(this.classLogger,super.getPlayerName() + ": Kept rolls is now: " + super.getKeptRolls().toString(), Level.DEBUG);

        // Since this player is maximizing combo's they will continue attempting to do so without stopping
        // i.e. they will not change their end turn boolean to true


    }

    private HashMap<Faces,Integer> checkCombinations(ArrayList<Faces> rolls) {
        // Setup HashMap to keep track of all face duplicates in the roll
        HashMap<Faces,Integer> setLog = new HashMap<Faces,Integer>();

        for (Faces currentFace : Faces.values()) {
            setLog.put(currentFace,0);
        }

        // Count all faces
        for (Faces currentRoll : rolls) {
            // Add 1 to the count of that specific face
            setLog.put(currentRoll,setLog.get(currentRoll) + 1);
        }

        return setLog;
    }


}
