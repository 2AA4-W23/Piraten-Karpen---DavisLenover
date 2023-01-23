package pk;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CardDeck {

    private ArrayList<Card> cardDeck;

    public CardDeck() {
        try {
            fillDeck();
        } catch (Exception exception) {
            ExceptionHandler.handleException(exception);
        }
    }


    public void shuffleDeck() throws EmptyDeckException {
        if (this.cardDeck.size() > 0) {
            Collections.shuffle(this.cardDeck);
        } else {
            throw new EmptyDeckException("Card deck empty! Re-adding cards is necessary", this);
        }
    }


    public Card drawCard() throws EmptyDeckException {
        if (this.cardDeck.size() > 0) {
            Card cardToReturn = this.cardDeck.get(0);
            this.cardDeck.remove(0);
            return cardToReturn;
        } else {
            throw new EmptyDeckException("Card deck empty! Re-adding cards is necessary", this);
        }
    }

    public void fillDeck() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        // Create Hashmap for each card type and how many there should be
        HashMap<Card,Integer> cardLegend = new HashMap<Card,Integer>();
        cardLegend.put(new SeaBattleCard(),6);
        cardLegend.put(new NopCard(),29);

        // Loop through every card type
        for (Card currentCard : cardLegend.keySet()) {

            // Add x amount of cards to the card deck
            for (int numOfCard = 1; numOfCard <= cardLegend.get(currentCard); numOfCard++) {
                // To add the card, create a new instance of it
                cardDeck.add(currentCard.getClass().getConstructor().newInstance());
            }
        }

    }

}
