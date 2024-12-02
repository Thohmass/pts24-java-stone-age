package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

public final class ToolMakerHutFields {
    private PlayerOrder[] toolMakerFigures;
    private PlayerOrder[] hutFigures;
    private PlayerOrder[] fieldsFigures;
    private int restriction;

    private Boolean canToolMaker;
    private Boolean canHut;
    private Boolean canFields;

    public ToolMakerHutFields() {
        this.toolMakerFigures = new PlayerOrder[1];
        this.hutFigures = new PlayerOrder[2];
        this.fieldsFigures = new PlayerOrder[1];

        canToolMaker = null;
        canHut = null;
        canFields = null;
    }

    public boolean placeOnToolMaker(final Player player) {
        if (canPlaceOnToolMaker(player)) {
            toolMakerFigures[0] = player.playerOrder();
            canToolMaker = true;
            return true;
        }
        return false;
    }

    public boolean actionToolMaker(final Player player) {
        if (toolMakerFigures[0].equals(player.playerOrder())) {
            player.playerBoard().giveEffect(new Effect[] { Effect.TOOL });
            canToolMaker = false;
            return true;
        }
        return false;
    }

    public boolean canPlaceOnToolMaker(final Player player) {
        return toolMakerFigures[0] == null;
    }

    public boolean placeOnHut(final Player player) {
        if (canPlaceOnHut(player)) {
            player.playerBoard().takeFigures(2);
            for (int i = 0; i < 2; i++) {
                hutFigures[i] = player.playerOrder();
            }
            canHut = true;
            return true;
        }
        return false;
    }

    public boolean actionHut(final Player player) {
        if (hutFigures[0].equals(player.playerOrder())) {
            player.playerBoard().giveFigure();
            canHut = false;
            return true;
        }
        return false;
    }

    public boolean canPlaceOnHut(final Player player) {
        return hutFigures[0] == null;
    }

    public boolean placeOnFields(final Player player) {
        if (canPlaceOnFields(player)) {
            fieldsFigures[0] = player.playerOrder();
            canFields = true;
            return true;
        }
        return false;
    }

    public boolean actionFields(final Player player) {
        if (fieldsFigures[0].equals(player.playerOrder())) {
            player.playerBoard().giveEffect(new Effect[] { Effect.FIELD });
            canFields = false;
            return true;
        }
        return false;
    }

    public boolean canPlaceOnFields(final Player player) {
        return fieldsFigures[0] == null;
    }

    public boolean newTurn() {
        if (canHut == null) {
            canHut = false;
        }
        if (canToolMaker == null) {
            canToolMaker = false;
        }
        if (canFields == null) {
            canFields = false;
        }

        if (!(canHut && canFields && canToolMaker)) {
            this.toolMakerFigures = new PlayerOrder[1];
            this.hutFigures = new PlayerOrder[2];
            this.fieldsFigures = new PlayerOrder[1];

            canToolMaker = null;
            canHut = null;
            canFields = null;

            return true;
        }
        return false;
    }

    public boolean getCanHut() {
        return canHut != null && canHut;
    }

    public boolean getCanFields() {
        return canFields != null && canFields;
    }

    public boolean getCanToolMaker() {
        return canToolMaker != null && canToolMaker;
    }

    public String state() {
        return "";
    }
}
