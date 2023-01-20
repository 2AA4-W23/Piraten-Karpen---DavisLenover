package pk;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerCombo extends Player {

    public PlayerCombo(String playerName, int numberOfDice) {
        super(playerName, numberOfDice);
    }

    public void strategy() {
        // Player will re-roll until at least one set of dice is made (2 or greater)
        // Player will keep these dice and re-roll, looking to maximize said combination
        // If a different set of dice appear, evaluate if it is possible to keep said set
        HashMap<Faces,Integer> keptCombinations = checkCombinations(super.getKeptRolls());
        HashMap<Faces,Integer> rolledCombinations = checkCombinations(super.getCurrentRoll());

        // Skip skulls as they are by default added to the player's kept rolls

        if (keptCombinations.isEmpty()) {
            for (Faces currentFace : Faces.values()) {
                if (rolledCombinations.get(currentFace) >= 2 && currentFace != Faces.SKULL && super.getKeptRolls().size() < 6) {
                    super.keepAllRolls(currentFace);
                }
            }
        } else {
            for (Faces currentFace : Faces.values()) {
                /* A combo player currently maximizes combos in two ways
                1. Check if they kept at least two of the given roll
                2. If a new combination appears that is at least a set of 2 and there is at least one extra space after adding to the kept rolls
                 */
                if (rolledCombinations.get(currentFace) >= 1 && keptCombinations.get(currentFace) >= 2 && currentFace != Faces.SKULL) {
                    super.keepAllRolls(currentFace);
                } else if (rolledCombinations.get(currentFace) >= 2 && keptCombinations.get(currentFace) >= 0 && super.getKeptRolls().size() < 8 - rolledCombinations.get(currentFace) && currentFace != Faces.SKULL) {
                    super.keepAllRolls(currentFace);
                }
            }
        }


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