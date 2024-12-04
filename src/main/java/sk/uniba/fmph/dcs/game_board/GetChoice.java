package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

public class GetChoice implements EvaluateCivilisationCardImmediateEffect {

    private int numberOfResources;

    /**
     *
     * @param numberOfResources
     *            - array containing the resources
     */

    public GetChoice(final Effect[] numberOfResources) {
        this.numberOfResources = numberOfResources.length;
    }

    /**
     *
     * @param player
     *            - current player
     * @param choice
     *            - Effect that player want to choose
     *
     * @return - True if the player got the effect, false otherwise
     */

    @Override
    public Boolean performEffect(final Player player, final Effect choice) {
        if (choice.isResource()) {
            if (numberOfResources > 0) {
                numberOfResources--;
                player.playerBoard().giveEffect(new Effect[] { choice });
                return true;
            }
        }
        return false;
    }
}
