package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.InterfaceToolUse;

public class CurrentThrow implements InterfaceToolUse {

    private Effect throwsFor;
    private int throwResult;

    public CurrentThrow(final Effect throwsFor, final int throwResult) {
        this.throwsFor = throwsFor;
        this.throwResult = throwResult;
    }

    /**
     *
     * @param player
     *            - current player
     * @param effect
     *            - effect
     * @param dices
     *            - number of dices
     */

    public void initiate(final Player player, final Effect effect, final int dices) {
        // todo
    }

    /**
     *
     * @return - string
     */

    public String state() {
        // todo
        return null;
    }

    /**
     *
     * @param idx
     *
     * @return
     */

    @Override
    public boolean useTool(final int idx) {
        // todo
        return false;
    }

    /**
     *
     * @return
     */

    @Override
    public boolean canUseTools() {
        // todo
        return false;
    }

    /**
     *
     * @return
     */

    @Override
    public boolean finishUsingTools() {
        // todo
        return false;
    }
}
