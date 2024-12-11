package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.io.File;
import java.util.Collection;
import java.util.Stack;

public abstract class BuildingFactory {

    protected final File file = new File("");

    public Building create(Effect[] resources, int numberOfResources, int numberOfResourceTypes) {
        return createBuilding(resources, numberOfResources, numberOfResourceTypes);
    }

    public Collection<Building> createBuildingStack(int size) {
        Stack<Building> stack = new Stack<>();
        for (int i = 0; i < size; i++) {
            stack.push(createBuilding(null, 0, 0)); // TODO
        }
        return stack;
    }

    protected abstract Collection<Building> createBuildingsFromJSON();

    protected abstract Building createBuilding(Effect[] resources, int numberOfResources, int numberOfResourceTypes);
}
