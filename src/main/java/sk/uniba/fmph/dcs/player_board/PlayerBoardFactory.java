package sk.uniba.fmph.dcs.player_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

public final class PlayerBoardFactory {

    private PlayerBoardFactory() { }

    public static PlayerBoard createPlayerBoard() {
        PlayerResourcesAndFood playerResourcesAndFood = new PlayerResourcesAndFood();
        PlayerFigures playerFigures = new PlayerFigures();
        PlayerCivilisationCards playerCivilisationCards = new PlayerCivilisationCards();
        PlayerTools playerTools = new PlayerTools();
        TribeFedStatus tribeFedStatus = new TribeFedStatus(playerFigures, playerResourcesAndFood);

        return new PlayerBoard(playerResourcesAndFood, playerFigures, playerCivilisationCards,
                playerTools, tribeFedStatus);
    }

    public static PlayerBoard createPlayerBoardWithSpecificValues(final Effect[] initialResources, final int extraInitialFigures,
                                                         final int initialTools, final boolean tribeFed) {
        PlayerResourcesAndFood playerResourcesAndFood = new PlayerResourcesAndFood();
        playerResourcesAndFood.giveResources(initialResources);

        PlayerFigures playerFigures = new PlayerFigures();
        for (int i = 0; i < extraInitialFigures; i++) {
            playerFigures.addNewFigure();
        }

        PlayerCivilisationCards playerCivilisationCards = new PlayerCivilisationCards();

        PlayerTools playerTools = new PlayerTools();
        for (int i = 0; i < initialTools; i++) {
            playerTools.addTool();
        }

        TribeFedStatus tribeFedStatus = new TribeFedStatus(playerFigures, playerResourcesAndFood);
        if (tribeFed) {
            tribeFedStatus.setTribeFed();
        }

        return new PlayerBoard(playerResourcesAndFood, playerFigures, playerCivilisationCards,
                playerTools, tribeFedStatus);
    }
}
