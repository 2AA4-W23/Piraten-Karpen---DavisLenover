package pk;

public class SeaBattleCard extends Card {

    public final int numberOfSabers;
    public final int bonusPoints;

    public SeaBattleCard(int numberOfSabers,int bonusPoints) {
        super(FortuneCards.SEA_BATTLE);
        this.numberOfSabers = numberOfSabers;
        this.bonusPoints = bonusPoints;
    }

    public void cardEffect(Player player) {
        return;
    }

    public int getNumberOfSabers() {
        return this.numberOfSabers;
    }

    public int getBonusPoints() {
        return this.bonusPoints;
    }

}
