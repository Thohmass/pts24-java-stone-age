package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.OptionalInt;

public final class ArbitraryBuilding implements Building {
    private final static int MAX_NUMBER_OF_RESOURCES = 7;

    public ArbitraryBuilding() {
    }

    @Override
    public OptionalInt build(final Effect[] resources) {
        // invalid move
        if (resources.length > MAX_NUMBER_OF_RESOURCES || resources.length == 0) {
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
