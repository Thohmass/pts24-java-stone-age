package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.CivilisationCard;
import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.Optional;

public class GetCard implements EvaluateCivilisationCardImmediateEffect {
    private final CivilizationCardDeck deck;

    public GetCard(final CivilizationCardDeck deck) {
        if (deck == null) {
            throw new IllegalArgumentException("Deck cannot be null");
        }
        this.deck = deck;
    }

    @Override
    public Boolean performEffect(final Player player, final Effect choice) {
        return deck.getTop()
                .map(card -> {
                    player.playerBoard().giveEndOfGameEffect(card.endOfGameEffect());
                    return true;
                })
                .orElse(false);
    }
}
