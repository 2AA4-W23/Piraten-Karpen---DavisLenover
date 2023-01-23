package pk;

import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {

    private ArrayList<Card> cardDeck;

    public CardDeck(ArrayList<Card> cardsToAdd) {
        this.cardDeck = cardsToAdd;
    }


    public void shuffleDeck() throws EmptyDeckException {
        if (this.cardDeck.size() > 0) {
            Collections.shuffle(this.cardDeck);
        } else {
            throw new EmptyDeckException("Card deck empty! Re-adding cards is necessary");
        }
    }


    public Card drawCard() throws EmptyDeckException {
        if (this.cardDeck.size() > 0) {
            Card cardToReturn = this.cardDeck.get(0);
            this.cardDeck.remove(0);
            return cardToReturn;
        } else {
            throw new EmptyDeckException("Card deck empty! Re-adding cards is necessary");
        }
    }

}
