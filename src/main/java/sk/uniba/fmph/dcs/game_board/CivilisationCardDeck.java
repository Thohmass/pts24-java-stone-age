package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.CivilisationCard;

import java.util.Collection;
import java.util.Optional;
import java.util.Stack;

public final class CivilisationCardDeck {

    private final Stack<CivilisationCard> civilisationCards;

    public CivilisationCardDeck(final Collection<CivilisationCard> civilisationCards) {
        this.civilisationCards = new Stack<>();
        this.civilisationCards.addAll(civilisationCards);
    }

    public Optional<CivilisationCard> getTop() {
        if (civilisationCards.isEmpty()) {
            return Optional.empty();
        }
        CivilisationCard civilisationCard = civilisationCards.pop();
        return Optional.of(civilisationCard);
    }

    public String state() {
        return "Cards in deck: "
                + civilisationCards.size();
    }
}
