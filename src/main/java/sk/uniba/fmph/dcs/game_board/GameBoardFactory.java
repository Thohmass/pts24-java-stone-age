package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.player_board.PlayerBoard;
import sk.uniba.fmph.dcs.player_board.PlayerBoardFactory;
import sk.uniba.fmph.dcs.player_board.PlayerBoardGameBoardFacade;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public final class GameBoardFactory{

    private GameBoardFactory() { }

    public static GameBoard createGameBoard(final int numberOfPlayers) throws IOException {
        CivilisationCardDeck civilisationCardDeck = new CivilisationCardDeck(new Stack<>() { });
        ArrayList<Building> buildings = BuildingsFactory.createBuildings();
        Player[] players = new Player[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
            PlayerOrder playerOrder = new PlayerOrder(i, numberOfPlayers);
            PlayerBoard playerBoard = PlayerBoardFactory.createPlayerBoard();
            PlayerBoardGameBoardFacade playerBoardGameBoardFacade = new PlayerBoardGameBoardFacade(playerBoard);
            players[i] = new Player(playerOrder, playerBoardGameBoardFacade);
        }

        return new GameBoard(civilisationCardDeck, buildings, players);
    }

    public static GameBoard createGameBoardWithSpecificValues(final int numberOfPlayers,
                                                         final CivilisationCardDeck civilisationCardDeck,
                                                         final ArrayList<Building> buildings, final Player[] players) {
        return new GameBoard(civilisationCardDeck, buildings, players);
    }
}
