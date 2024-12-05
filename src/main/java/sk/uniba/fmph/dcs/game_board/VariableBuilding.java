package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.HashSet;
import java.util.List;
import java.util.OptionalInt;

public final class VariableBuilding implements Building {
    private final int numberOfResources;
    private final int numberOfResourceTypes;

    private final int maxResources = 5;

    public VariableBuilding(final int numberOfResources, final int numberOfResourceTypes) {
        if (numberOfResources < 1) {
            throw new IllegalArgumentException("Number of resources must be greater than 0");
        } else if (numberOfResourceTypes < 1) {
            throw new IllegalArgumentException("Number of resource types must be greater than 0");
        } else if (numberOfResourceTypes > numberOfResources) {
            throw new IllegalArgumentException(
                    "Number of resource types must be less than or equal to number of resources");
        } else if (numberOfResources > maxResources) {
            throw new IllegalArgumentException("Number of resources must be less than or equal to 5");
        }

        this.numberOfResources = numberOfResources;
        this.numberOfResourceTypes = numberOfResourceTypes;
    }

    @Override
    public OptionalInt build(final Effect[] resources) {
        if (resources.length != numberOfResources || (new HashSet<>(List.of(resources))).size() != numberOfResourceTypes) {
            return OptionalInt.empty();
        }

        int sum = 0;
        for (Effect effect : resources) {
            if (!effect.isResource()) {
                return OptionalInt.empty();
            }
            sum += effect.points();
        }
        return OptionalInt.of(sum);
    }
}
