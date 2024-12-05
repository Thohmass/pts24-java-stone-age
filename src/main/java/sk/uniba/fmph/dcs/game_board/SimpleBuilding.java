package sk.uniba.fmph.dcs.game_board;

import java.util.Arrays;
import java.util.OptionalInt;

import sk.uniba.fmph.dcs.stone_age.Effect;

public final class SimpleBuilding implements Building {
    private final Effect[] requiredResources;

    public SimpleBuilding(final Effect[] resources) {
        for (Effect resource : resources) {
            if (!resource.isResource()) {
                throw new IllegalArgumentException("Resources must be resources");
            }
        }
        this.requiredResources = resources.clone();
    }

    public OptionalInt build(final Effect[] resources) {
        if (!Arrays.equals(this.requiredResources, resources)) {
            return OptionalInt.empty();
        }

        int sum = 0;
        for (Effect resource : resources) {
            sum += resource.points();
        }
        return OptionalInt.of(sum);
    }
}
