package pk;

public class NopCard extends Card {


    public NopCard() {
        super(FortuneCards.NOP);
    }

    public void cardEffect(Player player) {
        return;
    }
}
