package pk;

import org.apache.logging.log4j.Level;

public class EmptyDeckException extends GameException {

    public final CardDeck cardDeckInstance;

    private final Level serverityLevel = Level.WARN;

    public EmptyDeckException(String exceptionMessage,CardDeck cardDeck) {
        super(exceptionMessage);
        this.cardDeckInstance = cardDeck;

    }

    public Level getServerityLevel() {
        return this.serverityLevel;
    }

    public CardDeck getCardDeck() {
        return this.cardDeckInstance;
    }


}
