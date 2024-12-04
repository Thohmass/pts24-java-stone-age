package sk.uniba.fmph.dcs.player_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.Arrays;

public final class TribeFedStatus {
    private boolean tribeFed;
    private int fields;
    private final int maxFields = 10;
    private final PlayerFigures playerFigures;
    private final PlayerResourcesAndFood playerResourcesAndFood;

    public TribeFedStatus(final PlayerFigures playerFigures, final PlayerResourcesAndFood playerResourcesAndFood) {
        this.playerFigures = playerFigures;
        this.playerResourcesAndFood = playerResourcesAndFood;
        fields = 0;
        tribeFed = false;
    }

    public void addField() {
        if (fields < maxFields) {
            fields++;
        }
    }

    public void newTurn() {
        tribeFed = false;
    }

    public boolean feedTribeIfEnoughFood() {
        int foodToSpend = Math.max(playerFigures.getTotalFigures() - fields, 0);
        Effect[] food = new Effect[foodToSpend];
        Arrays.fill(food, Effect.FOOD);

        if (!playerResourcesAndFood.hasResources(food)) {
            return false;
        }
        return feedTribe(food);
    }

    public boolean feedTribe(final Effect[] resources) {
        if (playerFigures.getTotalFigures() - fields != resources.length) {
            return false;
        }
        boolean result = playerResourcesAndFood.takeResources(resources);
        if (!result) {
            return false;
        }
        tribeFed = true;
        return tribeFed;
    }

    public boolean setTribeFed() {
        this.tribeFed = true;
        return tribeFed;
    }

    public boolean isTribeFed() {
        return tribeFed;
    }

    public String state() {
        return "Tribe fed: " + tribeFed + "; Fields count: " + fields;
    }
}
