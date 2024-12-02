package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

public class GetSomethingThrow implements EvaluateCivilisationCardImmediateEffect {

    private final Effect resource;
    private final CurrentThrow currentThrow;

    public GetSomethingThrow(final Effect resource, final CurrentThrow currentThrow) {
        this.currentThrow = currentThrow;
        if (resource.isResource()) {
            this.resource = resource;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     *
     * @param player
     *            - current player
     * @param choice
     *            - what Effect does player want
     *
     * @return - whether the effect was initiated
     */

    @Override
    public Boolean performEffect(final Player player, final Effect choice) {
        if (!this.resource.equals(choice)) {
            return false;
        }

        currentThrow.initiate(player, choice, 2);
        return true;
    }
}
