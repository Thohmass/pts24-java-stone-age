package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.InterfaceToolUse;

import java.util.Arrays;
import java.util.Optional;

public final class CurrentThrow implements InterfaceToolUse {

    private Effect throwsFor;
    private int throwResult;
    private Player player;

    public CurrentThrow(final Effect throwsFor) {
        this.throwsFor = throwsFor;
        this.throwResult = 0;
        player = null;
    }

    public void initiate(final Player player, final Effect effect, final int dices) {
        this.player = player;
        throwsFor = effect;
        throwResult = Arrays.stream(new Throw().throwDice(dices)).sum();
    }

    public String state() {
        return "";
    }

    @Override
    public boolean useTool(final int idx) {
        Optional<Integer> tool = player.playerBoard().useTool(idx);
        if (tool.isEmpty()) {
            return false;
        }
        throwResult += tool.orElse(0);
        return true;
    }

    @Override
    public boolean canUseTools() {
        int goal = throwResult % throwsFor.points();
        return player.playerBoard().hasSufficientTools(goal);
    }

    @Override
    public boolean finishUsingTools() {
        int quantity = Math.floorDiv(throwResult, throwsFor.points());
        Effect[] reward = new Effect[quantity];
        Arrays.fill(reward, throwsFor);
        player.playerBoard().giveEffect(reward);
        return true;
    }
}
