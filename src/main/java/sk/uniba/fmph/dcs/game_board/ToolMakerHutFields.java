package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.ArrayList;

public final class ToolMakerHutFields {
    private ArrayList<PlayerOrder> toolMakerFigures;
    private ArrayList<PlayerOrder> hutFigures;
    private ArrayList<PlayerOrder> fieldsFigures;
    private static final int MAX_FIGURES = 1;
    private final int restriction;
    private final int noRestrictionPlayers = 4;
    private final int noRestriction = 3;

    private Boolean canToolMaker;
    private Boolean canHut;
    private Boolean canFields;

    public ToolMakerHutFields(final int players) {
        this.toolMakerFigures = new ArrayList<>();
        this.hutFigures = new ArrayList<>();
        this.fieldsFigures = new ArrayList<>();

        canToolMaker = false;
        canHut = false;
        canFields = false;

        restriction = players < noRestrictionPlayers ? noRestriction - 1 : noRestriction;
    }

    public boolean placeOnToolMaker(final Player player) {
        if (canPlaceOnToolMaker(player)) {
            toolMakerFigures.add(player.playerOrder());
            player.playerBoard().takeFigures(MAX_FIGURES);
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
        return toolMakerFigures.isEmpty() && player.playerBoard().hasFigures(MAX_FIGURES) && canPlace();
    }

    public boolean canActionToolMaker(final Player player) {
        return canToolMaker && toolMakerFigures.contains(player.playerOrder());
    }

    public boolean placeOnHut(final Player player) {
        if (canPlaceOnHut(player)) {
            player.playerBoard().takeFigures(2);
            for (int i = 0; i < MAX_FIGURES + 1; i++) {
                hutFigures.add(player.playerOrder());
                player.playerBoard().takeFigures(MAX_FIGURES);
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
        return hutFigures.isEmpty() && player.playerBoard().hasFigures(MAX_FIGURES + 1) && canPlace();
    }


    public boolean canActionHut(final Player player) {
        return canHut && hutFigures.contains(player.playerOrder());
    }

    public boolean placeOnFields(final Player player) {
        if (canPlaceOnFields(player)) {
            fieldsFigures.add(player.playerOrder());
            player.playerBoard().takeFigures(MAX_FIGURES);
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
        return fieldsFigures.isEmpty() && player.playerBoard().hasFigures(MAX_FIGURES) && canPlace();
    }

    public boolean canActionFields(final Player player) {
        return canFields && fieldsFigures.contains(player.playerOrder());
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
            this.toolMakerFigures.clear();
            this.hutFigures.clear();
            this.fieldsFigures.clear();

            canToolMaker = null;
            canHut = null;
            canFields = null;

            return true;
        }
        return false;
    }

    private boolean canPlace() {
        int filled = 0;
        if (!toolMakerFigures.isEmpty()) {
            filled++;
        }
        if (!hutFigures.isEmpty()) {
            filled++;
        }
        if (!fieldsFigures.isEmpty()) {
            filled++;
        }
        return filled < restriction;
    }

    public String state() {
        return "";
    }
}
