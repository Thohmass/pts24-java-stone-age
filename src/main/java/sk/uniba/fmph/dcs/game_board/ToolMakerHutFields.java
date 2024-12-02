package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

public final class ToolMakerHutFields {
    private PlayerOrder[] toolMakerFigures;
    private PlayerOrder[] hutFigures;
    private PlayerOrder[] fieldsFigures;
    private final int restriction;

    private final int noRestrictionPlayers = 4;
    private final int noRestriction = 3;

    private Boolean canToolMaker;
    private Boolean canHut;
    private Boolean canFields;

    public ToolMakerHutFields(final int players) {
        this.toolMakerFigures = new PlayerOrder[1];
        this.hutFigures = new PlayerOrder[2];
        this.fieldsFigures = new PlayerOrder[1];

        canToolMaker = null;
        canHut = null;
        canFields = null;

        restriction = players < noRestrictionPlayers ? noRestriction - 1 : noRestriction;
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
        if (canActionToolMaker(player)) {
            player.playerBoard().giveEffect(new Effect[] { Effect.TOOL });
            canToolMaker = false;
            return true;
        }
        return false;
    }

    public boolean canPlaceOnToolMaker(final Player player) {
        return toolMakerFigures[0] == null && canPlace();
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
        if (canActionHut(player)) {
            player.playerBoard().giveFigure();
            canHut = false;
            return true;
        }
        return false;
    }

    public boolean canPlaceOnHut(final Player player) {
        return hutFigures[0] == null && canPlace();
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
        if (canActionFields(player)) {
            player.playerBoard().giveEffect(new Effect[] { Effect.FIELD });
            canFields = false;
            return true;
        }
        return false;
    }

    public boolean canPlaceOnFields(final Player player) {
        return fieldsFigures[0] == null && canPlace();
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

    public boolean canActionHut(final Player player) {
        return canHut != null && canHut && hutFigures[0].equals(player.playerOrder());
    }

    public boolean canActionToolMaker(final Player player) {
        return canToolMaker != null && canToolMaker && toolMakerFigures[0].equals(player.playerOrder());
    }

    public boolean canActionFields(final Player player) {
        return canFields != null && canFields && fieldsFigures[0].equals(player.playerOrder());
    }

    private boolean canPlace() {
        int filled = 0;
        if (toolMakerFigures[0] == null) {
            filled++;
        }
        if (hutFigures[0] == null) {
            filled++;
        }
        if (fieldsFigures[0] == null) {
            filled++;
        }
        return filled < restriction;
    }

    public String state() {
        return "";
    }
}
