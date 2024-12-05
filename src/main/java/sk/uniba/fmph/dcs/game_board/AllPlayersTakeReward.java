package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

public final class AllPlayersTakeReward implements EvaluateCivilisationCardImmediateEffect {

    private final RewardMenu rewardMenu;

    public AllPlayersTakeReward(final RewardMenu rewardMenu) {
        this.rewardMenu = rewardMenu;
    }


    @Override
    public Boolean performEffect(final Player player, final Effect choice) {
        if (!rewardMenu.takeReward(player.playerOrder(), choice)) {
            return false;
        }
        player.playerBoard().giveEffect(new Effect[]{choice});
        return true;
    }
}
