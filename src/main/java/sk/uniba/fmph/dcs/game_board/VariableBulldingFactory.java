package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.Collection;

public class VariableBulldingFactory extends BuildingFactory {
    @Override
    protected Collection<Building> createBuildingsFromJSON() {
        return null;
    }

    @Override
    protected Building createBuilding(Effect[] resources, int numberOfResources, int numberOfResourceTypes) {
        return new VariableBuilding(numberOfResources, numberOfResourceTypes);
    }
}
