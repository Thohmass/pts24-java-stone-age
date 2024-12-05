package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.CivilisationCard;
import sk.uniba.fmph.dcs.stone_age.Effect;

public class GetCard implements EvaluateCivilisationCardImmediateEffect {
    private final CivilisationCard card;

    public GetCard(final CivilisationCard card) {
        this.card = card;
    }

    public final String state() {
        return "get card";

    }

    @Override
    public final Boolean performEffect(final Player player, final Effect choice) {
        player.playerBoard().giveEndOfGameEffect(card.endOfGameEffect());
        return true;
    }
}
