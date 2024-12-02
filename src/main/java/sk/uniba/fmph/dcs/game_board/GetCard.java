package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.CivilisationCard;
import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.Optional;

public class GetCard implements EvaluateCivilisationCardImmediateEffect {
    private final CivilizationCardDeck deck;

    public GetCard(final CivilizationCardDeck deck) {
        this.deck = deck;
    }

    @Override
    public final Boolean performEffect(final Player player, final Effect choice) {
        Optional<CivilisationCard> c = deck.getTop();
        if (c.isEmpty()) {
            return false;
        }
        player.playerBoard().giveEndOfGameEffect(c.get().endOfGameEffect());
        return true;
    }
}
