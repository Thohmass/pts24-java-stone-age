package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

public class GetCard implements EvaluateCivilisationCardImmediateEffect {
    private final CivilizationCardDeck deck;

    public GetCard(final CivilizationCardDeck deck) {
        this.deck = deck;
    }

    @Override
    public final Boolean performEffect(final Player player, final Effect choice) {
        return false;

    }
}
