package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.CivilisationCard;
import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.Optional;

public final class GetCard implements EvaluateCivilisationCardImmediateEffect {

    private final CivilisationCardDeck civilisationCardDeck;

    public GetCard(final CivilisationCardDeck civilisationCardDeck) {
        this.civilisationCardDeck = civilisationCardDeck;
    }

    @Override
    public Boolean performEffect(final Player player, final Effect choice) {
        Optional<CivilisationCard> card = civilisationCardDeck.getTop();
        if (card.isEmpty()) {
            return false;
        }
        player.playerBoard().giveEndOfGameEffect(card.get().endOfGameEffect());
        return true;
    }
}
