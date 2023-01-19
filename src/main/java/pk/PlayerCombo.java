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
        // If a different combination of dice appear evaluate if it is worth keeping that combination
        HashMap<Faces,Integer> combinations = checkCombinations(super.getCurrentRoll());

        if (super.getKeptRolls().size() == 0) {
            for (Faces currentFace : Faces.values()) {
                if (combinations.get(currentFace) >= 2) {

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
