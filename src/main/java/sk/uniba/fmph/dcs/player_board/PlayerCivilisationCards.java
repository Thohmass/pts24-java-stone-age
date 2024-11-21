package sk.uniba.fmph.dcs.player_board;

import sk.uniba.fmph.dcs.stone_age.EndOfGameEffect;

import java.util.HashMap;
import java.util.Map;

public final class PlayerCivilisationCards {
    private final Map<EndOfGameEffect, Integer> endOfGameEffects;

    public PlayerCivilisationCards() {
        endOfGameEffects = new HashMap<>();
        for (EndOfGameEffect endOfGameEffect : EndOfGameEffect.values()) {
            endOfGameEffects.put(endOfGameEffect, 0);
        }
    }

    public void addEndOfGameEffects(final EndOfGameEffect[] effects) {
        // Integer in map represents effect count
        for (EndOfGameEffect effect : effects) {
            endOfGameEffects.put(effect, endOfGameEffects.get(effect) + 1);
        }
    }

    public int calculateEndOfGameCivilisationCardPoints(final int buildings, final int tools, final int fields,
            final int figures) {
        EndOfGameEffect[] greenBackground = {EndOfGameEffect.MEDICINE, EndOfGameEffect.ART, EndOfGameEffect.WRITING,
                EndOfGameEffect.POTTERY, EndOfGameEffect.SUNDIAL, EndOfGameEffect.TRANSPORT, EndOfGameEffect.MUSIC,
                EndOfGameEffect.WEAVING};
        int sum = 0;

        // go through green background cards, count layers (sets) and points for sets
        int layer = 1;
        while (true) {
            int points = 0;
            for (EndOfGameEffect effect : greenBackground) {
                if (endOfGameEffects.get(effect) >= layer) {
                    points++;
                }
            }
            if (points == 0) {
                break;
            }
            layer++;
            sum += points * points;
        }

        sum += Math.max(fields, 0) * endOfGameEffects.get(EndOfGameEffect.FARMER);
        sum += Math.max(tools, 0) * endOfGameEffects.get(EndOfGameEffect.TOOL_MAKER);
        sum += Math.max(buildings, 0) * endOfGameEffects.get(EndOfGameEffect.BUILDER);
        sum += Math.max(figures, 0) * endOfGameEffects.get(EndOfGameEffect.SHAMAN);
        return sum;
    }

    public String state() {
        return "";
    }
}
