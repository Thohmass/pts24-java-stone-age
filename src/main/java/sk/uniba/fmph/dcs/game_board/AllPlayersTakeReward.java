package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

public class AllPlayersTakeReward implements EvaluateCivilisationCardImmediateEffect{

    private final RewardMenu rewardMenu;

    public AllPlayersTakeReward(final RewardMenu rewardMenu) {
        this.rewardMenu = rewardMenu;
    }


    @Override
    public Boolean performEffect(Player player, Effect choice) {
        if (!rewardMenu.takeReward(player.playerOrder(), choice)) {
            return false;
        }
        player.playerBoard().giveEffect(new Effect[]{choice});
        return true;
    }
}
