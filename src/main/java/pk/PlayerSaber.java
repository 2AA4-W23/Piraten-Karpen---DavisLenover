package pk;

import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerSaber extends PlayerCombo {


    public PlayerSaber(String playerName, int numberOfDice) {
        super(playerName, numberOfDice);
    }

    public void strategy() {

        // This strategy is a mutation of the PlayerCombo class strategy
        // Check if the player has a saber card and if so check how many sabers are needed to win points
        // Only choose saber rolls to keep until amount of sabers needed is met

        // If not, just use PlayerCombo strategy

        if (this.getCard().getCardType() == FortuneCards.SEA_BATTLE) {

            ArrayList<Faces> currentRoll = super.getCurrentRoll();
            ArrayList<Faces> keptRolls = super.getKeptRolls();

            DevTools.logMessage(this.classLogger, super.getPlayerName() + ": Checking for sets in: " + currentRoll.toString() + " given kept rolls of: " + keptRolls.toString(), Level.DEBUG);

            HashMap<Faces, Integer> keptCombinations = checkCombinations(keptRolls);
            HashMap<Faces, Integer> rolledCombinations = checkCombinations(currentRoll);

            // Check if amount of sabers has not been obtained yet
            if (keptCombinations.get(Faces.SABER) < ((SeaBattleCard) this.getCard()).getNumberOfSabers()) {
                // Keep checking for sabers and if they appear, keep them
                int faceCount = rolledCombinations.get(Faces.SABER);
                if (faceCount >= 1) {
                    DevTools.logMessage(this.classLogger, super.getPlayerName() + ": Found set of another " + faceCount + " " + Faces.SABER.toString() + " to keep", Level.DEBUG);
                    super.keepAllRolls(Faces.SABER);
                }
            } else {
                super.strategy();
            }
        } else {
            super.strategy();
        }
    }

}
