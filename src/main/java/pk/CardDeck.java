package pk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CardDeck {

    private ArrayList<Card> cardDeck;

    public CardDeck() {
        fillDeck();
    }


    public void shuffleDeck() {

        if (this.cardDeck.size() == 0) {
            fillDeck();
        }
        Collections.shuffle(this.cardDeck);
    }

    public Card drawCard() {

        if (this.cardDeck.size() == 0) {
            fillDeck();
        }

        Card cardToReturn = this.cardDeck.get(0);
        this.cardDeck.remove(0);
        return cardToReturn;

    }

    public void fillDeck() {

        // Create Hashmap for each card type and how many there should be
        HashMap<Card,Integer> cardLegend = new HashMap<>();
        cardLegend.put(new SeaBattleCard(2,300),2);
        cardLegend.put(new SeaBattleCard(3,500),2);
        cardLegend.put(new SeaBattleCard(4,1000),2);
        cardLegend.put(new MonkeyBusinessCard(),4);
        cardLegend.put(new NopCard(),25);

        this.cardDeck = new ArrayList<>();

        // Loop through every card type
        for (Card currentCard : cardLegend.keySet()) {
            // Add x amount of cards to the card deck
            for (int numOfCard = 1; numOfCard <= cardLegend.get(currentCard); numOfCard++) {
                // To add the card, create a new instance of it
                // Check main card instance for passing parameters into constructor
                if (currentCard instanceof SeaBattleCard) {
                    cardDeck.add(new SeaBattleCard(((SeaBattleCard) currentCard).getNumberOfSabers(), ((SeaBattleCard) currentCard).getBonusPoints()));
                } else if (currentCard instanceof MonkeyBusinessCard){
                    cardDeck.add(new MonkeyBusinessCard());
                } else {
                    cardDeck.add(new NopCard());
                }
            }
        }

    }

}
