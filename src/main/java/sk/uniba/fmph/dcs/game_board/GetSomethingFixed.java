package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.ArrayList;
import java.util.List;

public class GetSomethingFixed implements EvaluateCivilisationCardImmediateEffect {

    private final List<Effect> fixed;

    public GetSomethingFixed(final Effect[] fixed) {
        this.fixed = new ArrayList<>();
        for (int i = 0; i < fixed.length; i++) {
            this.fixed.add(fixed[i]);
        }
    }

    /**
     *
     * @param player
     *            - current player
     * @param choice
     *            - the choice is here irelevant
     *
     * @return - true, player always get all the resources back
     */

    @Override
    public Boolean performEffect(final Player player, final Effect choice) {
        Effect[] effects = new Effect[this.fixed.size()];
        for (int i = 0; i < effects.length; i++) {
            effects[i] = this.fixed.get(i);
        }
        player.playerBoard().giveEffect(effects);
        this.fixed.clear();
        return true;
    }
}
