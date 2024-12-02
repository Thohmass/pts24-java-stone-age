package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.CivilisationCard;

import java.util.Optional;
import java.util.Stack;

public final class CivilizationCardDeck {
    private final Stack<CivilisationCard> deck;

    public CivilizationCardDeck(final CivilisationCard[] cards) {
        this.deck = new Stack<>();
    }

    public Optional<CivilisationCard> getTop() {
        return Optional.empty();
    }

}
