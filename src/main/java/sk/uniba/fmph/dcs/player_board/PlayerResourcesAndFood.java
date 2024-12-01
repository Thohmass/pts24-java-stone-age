package sk.uniba.fmph.dcs.player_board;

import org.json.JSONObject;
import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.HashMap;
import java.util.Map;

public final class PlayerResourcesAndFood {
    private final Map<Effect, Integer> resources;
    private final int startingFood = 12;

    public PlayerResourcesAndFood() {
        this.resources = new HashMap<>();
        for (Effect effect : Effect.values()) {
            if (effect.isResourceOrFood()) {
                this.resources.put(effect, 0);
            }
        }

        this.resources.put(Effect.FOOD, startingFood);
    }

    public boolean hasResources(final Effect[] resources) {
        Map<Effect, Integer> resCount = resourcesToCountMap(resources);

        for (Effect res : resCount.keySet()) {
            if (this.resources.get(res) < resCount.get(res)) {
                return false;
            }
        }
        return true;
    }

    public boolean takeResources(final Effect[] resources) {
        if (!hasResources(resources)) {
            return false;
        }

        for (Effect res : resources) {
            this.resources.put(res, this.resources.get(res) - 1);
        }

        return true;
    }

    public boolean giveResources(final Effect[] resources) {
        Map<Effect, Integer> resCount = resourcesToCountMap(resources);

        for (Effect res : resCount.keySet()) {
            this.resources.put(res, this.resources.get(res) + resCount.get(res));
        }

        return true;
    }

    public int numberOfResourcesForFinalPoints() {
        int sum = 0;

        for (Effect res : resources.keySet()) {
            if (res.isResource()) {
                sum += resources.get(res);
            }
        }

        return sum;
    }

    public String state() {
        JSONObject state = new JSONObject();
        for (Effect res : resources.keySet()) {
            state.put(res.name(), resources.get(res));
        }
        return state.toString();
    }

    private Map<Effect, Integer> resourcesToCountMap(final Effect[] resources) {
        Map<Effect, Integer> resCount = new HashMap<>();

        for (Effect effect : resources) {
            if (!effect.isResourceOrFood()) {
                throw new IllegalArgumentException("Resources must be either resource or food");
            }
            resCount.put(effect, resCount.getOrDefault(effect, 0) + 1);
        }

        return resCount;
    }
}
