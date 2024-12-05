package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Building;
import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;

public final class GameBoard implements InterfaceGetState {

    private String state;
    private final ToolMakerHutFields toolMakerHutFields;
    private final CivilisationCardDeck civilisationCardDeck;
    private final CivilisationCardPlace civilisationCardPlace;
    private final RewardMenu rewardMenu;
    private Building[] tiles;
    public GameBoard(final RewardMenu rewardMenu, final ToolMakerHutFields toolMakerHutFields,
                     final CivilisationCardDeck civilisationCardDeck) {
        this.rewardMenu = rewardMenu;
        this.toolMakerHutFields = toolMakerHutFields;
        this.civilisationCardDeck = civilisationCardDeck;
        this.civilisationCardPlace = new CivilisationCardPlace(4,
                this.civilisationCardDeck, this);
        state = "Game started";
    }

    @Override
    public String state() {
        return state;
    }
}