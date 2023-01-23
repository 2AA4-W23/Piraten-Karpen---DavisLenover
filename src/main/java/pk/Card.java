package pk;

public abstract class Card {

    public FortuneCards cardType;

    public Card(FortuneCards cardType) {
        this.cardType = cardType;
    }

    public FortuneCards getCardType() {
        return this.cardType;
    }

    public abstract void cardEffect(Player player);

}
