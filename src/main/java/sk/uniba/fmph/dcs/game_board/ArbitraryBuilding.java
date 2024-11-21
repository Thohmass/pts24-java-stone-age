package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.Collection;
import java.util.OptionalInt;

public final class ArbitraryBuilding implements Building {
    private final int maxNumberOfResources = 7;

    public ArbitraryBuilding() {
    }

    @Override
    public OptionalInt build(final Collection<Effect> resources) {
        // invalid move
        if (resources.size() > maxNumberOfResources || resources.isEmpty()) {
            return OptionalInt.empty();
        }

        int sum = 0;
        for (Effect resource : resources) {
            if (!resource.isResource()) {
                return OptionalInt.empty();
            }
            sum += resource.points();
        }
        return OptionalInt.of(sum);
    }
}
