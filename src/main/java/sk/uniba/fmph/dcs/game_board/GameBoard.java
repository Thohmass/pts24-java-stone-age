package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;

public final class GameBoard implements InterfaceGetState {

    private String state;
    private final ToolMakerHutFields toolMakerHutFields;
    private final RewardMenu rewardMenu;
    private Building[] tiles;
    public GameBoard(final RewardMenu rewardMenu, final ToolMakerHutFields toolMakerHutFields) {
        this.rewardMenu = rewardMenu;
        this.toolMakerHutFields = toolMakerHutFields;
        state = "Game started";
    }

    @Override
    public String state() {
        return state;
    }
}
