package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;
import sk.uniba.fmph.dcs.stone_age.Location;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.Building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class GameBoard implements InterfaceGetState {

    private final Player[] players;
    private final ToolMakerHutFields toolMakerHutFields;
    private final CurrentThrow currentThrow;
    private final CivilisationCardDeck deck;
    private final RewardMenu rewardMenu;
    private final Map<Location, InterfaceFigureLocationInternal> locations;
    private static final int REQUIRED_RESOURCES_CARD4 = 1;
    private static final int REQUIRED_RESOURCES_CARD3 = 2;
    private static final int REQUIRED_RESOURCES_CARD2 = 3;
    private static final int REQUIRED_RESOURCES_CARD1 = 4;
    private static final int NUMBER_BUILDING_TILES = 4;

    public GameBoard(final CivilisationCardDeck deck, final ArrayList<Building> buildings,
                     final Player[] players, final int desiredResultThrow) {
        this.deck = deck;
        this.players = players.clone();
        currentThrow = new CurrentThrow(Effect.WOOD);
        rewardMenu = new RewardMenu(players);
        toolMakerHutFields = new ToolMakerHutFields(players.length);

        locations = new HashMap<>();
        locations.put(Location.TOOL_MAKER, new PlaceOnToolMakerAdaptor(toolMakerHutFields));
        locations.put(Location.HUT, new PlaceOnHutAdaptor(toolMakerHutFields));
        locations.put(Location.FIELD, new PlaceOnHutAdaptor(toolMakerHutFields));

        // Map<ImmediateEffect, EvaluateCivilisationCardImmediateEffect> evaluate = initializeEvaluationMap();

        locations.put(Location.HUNTING_GROUNDS,
                new ResourceSource("HuntingGrounds", Effect.FOOD, players.length, players.length, currentThrow));
        locations.put(Location.FOREST, new ResourceSource("Forest", Effect.WOOD, players.length,
                players.length - 1, currentThrow));
        locations.put(Location.CLAY_MOUND, new ResourceSource("ClayMound", Effect.CLAY, players.length,
                players.length - 1, currentThrow));
        locations.put(Location.QUARRY, new ResourceSource("Quarry", Effect.STONE, players.length,
                players.length - 1, currentThrow));
        locations.put(Location.RIVER, new ResourceSource("River", Effect.GOLD, players.length,
                players.length - 1, currentThrow));

        CivilisationCardPlace card4 = new CivilisationCardPlace(REQUIRED_RESOURCES_CARD4, deck, deck.getTop(), null);
        CivilisationCardPlace card3 = new CivilisationCardPlace(REQUIRED_RESOURCES_CARD3, deck, deck.getTop(), card4);
        CivilisationCardPlace card2 = new CivilisationCardPlace(REQUIRED_RESOURCES_CARD2, deck, deck.getTop(), card3);
        CivilisationCardPlace card1 = new CivilisationCardPlace(REQUIRED_RESOURCES_CARD1, deck, deck.getTop(), card2);
        locations.put(Location.CIVILISATION_CARD4, card4);
        locations.put(Location.CIVILISATION_CARD3, card3);
        locations.put(Location.CIVILISATION_CARD2, card2);
        locations.put(Location.CIVILISATION_CARD1, card1);

        ArrayList<Building> buildings1 = new ArrayList<>();
        ArrayList<Building> buildings2 = new ArrayList<>();
        ArrayList<Building> buildings3 = new ArrayList<>();
        ArrayList<Building> buildings4 = new ArrayList<>();
        for (int i = 0; i < buildings.size(); i++) {
            switch (i % NUMBER_BUILDING_TILES) {
                case 0 -> buildings1.add(buildings.get(i));
                case 1 -> buildings2.add(buildings.get(i));
                case 2 -> buildings3.add(buildings.get(i));
                case NUMBER_BUILDING_TILES - 1 -> buildings4.add(buildings.get(i));
                default -> { }
            }
        }
//        locations.put(Location.BUILDING_TILE1, new BuildingTile(buildings1));
//        locations.put(Location.BUILDING_TILE2, new BuildingTile(buildings2));
//        locations.put(Location.BUILDING_TILE3, new BuildingTile(buildings3));
//        locations.put(Location.BUILDING_TILE4, new BuildingTile(buildings4));
    }

    @Override
    public String state() {
        return "";
    }
}
