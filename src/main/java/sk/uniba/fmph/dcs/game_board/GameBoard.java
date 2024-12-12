package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;
import sk.uniba.fmph.dcs.stone_age.Location;
import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

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
    private static final int NUMBER_OF_BUILDING_TILES = 4;
    private static final int FIGURES_LIMIT_ON_RESOURCE_SOURCES = 7;
    private static final int FIGURES_LIMIT_ON_HUNTING_GROUNDS = Integer.MAX_VALUE;

    public GameBoard(final CivilisationCardDeck deck, final ArrayList<Building> buildings,
                     final Player[] players) {
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
                new ResourceSource("HuntingGrounds", Effect.FOOD, FIGURES_LIMIT_ON_HUNTING_GROUNDS,
                        FIGURES_LIMIT_ON_HUNTING_GROUNDS, currentThrow));
        locations.put(Location.FOREST, new ResourceSource("Forest", Effect.WOOD,
                FIGURES_LIMIT_ON_RESOURCE_SOURCES, players.length - 1, currentThrow));
        locations.put(Location.CLAY_MOUND, new ResourceSource("ClayMound", Effect.CLAY,
                FIGURES_LIMIT_ON_RESOURCE_SOURCES, players.length - 1, currentThrow));
        locations.put(Location.QUARRY, new ResourceSource("Quarry", Effect.STONE,
                FIGURES_LIMIT_ON_RESOURCE_SOURCES, players.length - 1, currentThrow));
        locations.put(Location.RIVER, new ResourceSource("River", Effect.GOLD,
                FIGURES_LIMIT_ON_RESOURCE_SOURCES, players.length - 1, currentThrow));

        CivilisationCardPlace card4 = new CivilisationCardPlace(REQUIRED_RESOURCES_CARD4, deck, deck.getTop(), null);
        CivilisationCardPlace card3 = new CivilisationCardPlace(REQUIRED_RESOURCES_CARD3, deck, deck.getTop(), card4);
        CivilisationCardPlace card2 = new CivilisationCardPlace(REQUIRED_RESOURCES_CARD2, deck, deck.getTop(), card3);
        CivilisationCardPlace card1 = new CivilisationCardPlace(REQUIRED_RESOURCES_CARD1, deck, deck.getTop(), card2);
        locations.put(Location.CIVILISATION_CARD4, card4);
        locations.put(Location.CIVILISATION_CARD3, card3);
        locations.put(Location.CIVILISATION_CARD2, card2);
        locations.put(Location.CIVILISATION_CARD1, card1);

        Stack<Building> buildings1 = new Stack<>();
        Stack<Building> buildings2 = new Stack<>();
        Stack<Building> buildings3 = new Stack<>();
        Stack<Building> buildings4 = new Stack<>();
        for (int i = 0; i < buildings.size(); i++) {
            switch (i % NUMBER_OF_BUILDING_TILES) {
                case 0 -> buildings1.add(buildings.get(i));
                case 1 -> buildings2.add(buildings.get(i));
                case 2 -> buildings3.add(buildings.get(i));
                case NUMBER_OF_BUILDING_TILES - 1 -> buildings4.add(buildings.get(i));
                default -> { }
            }
        }
        locations.put(Location.BUILDING_TILE1, new BuildingTile(buildings1));
        locations.put(Location.BUILDING_TILE2, new BuildingTile(buildings2));
        locations.put(Location.BUILDING_TILE3, new BuildingTile(buildings3));
        locations.put(Location.BUILDING_TILE4, new BuildingTile(buildings4));
    }

    public InterfaceFigureLocationInternal getLocation(final Location location) {
        return locations.get(location);
    }
    public Player[] getPlayers() {
        return players;
    }
    public CurrentThrow getCurrentThrow() {
        return currentThrow;
    }

    @Override
    public String state() {
        return "";
    }
}
