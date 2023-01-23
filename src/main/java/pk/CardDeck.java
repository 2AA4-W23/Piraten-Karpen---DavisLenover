package pk;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CardDeck {

    private ArrayList<Card> cardDeck;

    public CardDeck() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        fillDeck();
    }


    public void shuffleDeck() {

        if (this.cardDeck.size() == 0) {
            try {
                fillDeck();
            } catch (Exception exception) {
                ExceptionHandler.handleException(exception);
            }
        }

        Collections.shuffle(this.cardDeck);
    }


    public Card drawCard() {

        if (this.cardDeck.size() == 0) {
            try {
                fillDeck();
            } catch (Exception exception) {
                ExceptionHandler.handleException(exception);
            }
        }

        Card cardToReturn = this.cardDeck.get(0);
        this.cardDeck.remove(0);
        return cardToReturn;

    }

    public void fillDeck() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        // Create Hashmap for each card type and how many there should be
        HashMap<Card,Integer> cardLegend = new HashMap<Card,Integer>();
        cardLegend.put(new SeaBattleCard(2,300),2);
        cardLegend.put(new SeaBattleCard(3,500),2);
        cardLegend.put(new SeaBattleCard(4,1000),2);
        cardLegend.put(new NopCard(),29);

        // Loop through every card type
        for (Card currentCard : cardLegend.keySet()) {
            // Add x amount of cards to the card deck
            for (int numOfCard = 1; numOfCard <= cardLegend.get(currentCard); numOfCard++) {
                // To add the card, create a new instance of it
                // Check main card instance for passing parameters into constructor
                if (currentCard instanceof SeaBattleCard) {
                    cardDeck.add(currentCard.getClass().getConstructor().newInstance(((SeaBattleCard) currentCard).getNumberOfSabers(),((SeaBattleCard) currentCard).getBonusPoints()));
                } else {
                    cardDeck.add(currentCard.getClass().getConstructor().newInstance());
                }
            }
        }

    }

}
