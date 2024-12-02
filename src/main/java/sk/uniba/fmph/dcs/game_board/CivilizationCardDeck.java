package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.CivilisationCard;

import java.util.Optional;
import java.util.Stack;

public final class CivilizationCardDeck {
    private final Stack<CivilisationCard> deck;

    public CivilizationCardDeck(final CivilisationCard[] cards) {
        this.deck = new Stack<>();
        for (CivilisationCard card : cards) {
            this.deck.push(card);
        }
    }

    public Optional<CivilisationCard> getTop() {
        if (this.deck.empty()) {
            
            return Optional.empty();
        }

        return Optional.of(this.deck.pop());
    }

    public String state() {
        return "Deck size: " + this.deck.size();
    }
}