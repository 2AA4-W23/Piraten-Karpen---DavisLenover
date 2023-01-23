package pk;

public class SeaBattleCard extends Card {

    public final int numberOfSabers;

    public SeaBattleCard(int numberOfSabers) {
        super(FortuneCards.SEA_BATTLE);
        this.numberOfSabers = numberOfSabers;
    }

    public void cardEffect(Player player) {

    }

    public int getNumberOfSabers() {
        return this.numberOfSabers;
    }

}
