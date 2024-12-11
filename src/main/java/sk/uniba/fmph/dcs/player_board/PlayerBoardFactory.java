package sk.uniba.fmph.dcs.player_board;

import sk.uniba.fmph.dcs.game_board.Player;
import sk.uniba.fmph.dcs.stone_age.BoardFactory;
import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

public final class PlayerBoardFactory extends BoardFactory {

    @Override
    protected InterfaceGetState createBoard() {
        PlayerResourcesAndFood playerResourcesAndFood = new PlayerResourcesAndFood();
        PlayerFigures playerFigures = new PlayerFigures();
        PlayerCivilisationCards playerCivilisationCards = new PlayerCivilisationCards();
        PlayerTools playerTools = new PlayerTools();
        TribeFedStatus tribeFedStatus = new TribeFedStatus(playerFigures, playerResourcesAndFood);

        return new PlayerBoard(playerResourcesAndFood, playerFigures, playerCivilisationCards,
                playerTools, tribeFedStatus);
    }

    public static Player[] createPlayers(final int numberOfPlayers) {
        Player[] players = new Player[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
            PlayerOrder playerOrder = new PlayerOrder(i, numberOfPlayers);
            PlayerBoardFactory playerBoardFactory = new PlayerBoardFactory();
            PlayerBoard playerBoard = (PlayerBoard) playerBoardFactory.create();
            PlayerBoardGameBoardFacade playerBoardGameBoardFacade = new PlayerBoardGameBoardFacade(playerBoard);
            players[i] = new Player(playerOrder, playerBoardGameBoardFacade);
        }

        return players;
    }

    public static Player[] createPlayersWithSpecificValues(final int numberOfPlayers) {
        Player[] players = new Player[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
            PlayerOrder playerOrder = new PlayerOrder(i, numberOfPlayers);
            PlayerBoardFactory playerBoardFactory = new PlayerBoardFactory();
            PlayerBoard playerBoard = (PlayerBoard) playerBoardFactory.create();
            PlayerBoardGameBoardFacade playerBoardGameBoardFacade = new PlayerBoardGameBoardFacade(playerBoard);
            players[i] = new Player(playerOrder, playerBoardGameBoardFacade);
        }

        return players;
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
