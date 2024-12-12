package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.Collection;

public class ArbitraryBuildingFactory extends BuildingFactory {

    @Override
    protected Building createBuilding(Effect[] resources, int numberOfResources, int numberOfResourceTypes) {
        return new ArbitraryBuilding();
    }
}
