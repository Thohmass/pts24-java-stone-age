package sk.uniba.fmph.dcs.game_board;

import sk.uniba.fmph.dcs.player_board.PlayerBoard;
import sk.uniba.fmph.dcs.player_board.PlayerBoardFactory;
import sk.uniba.fmph.dcs.player_board.PlayerBoardGameBoardFacade;
import sk.uniba.fmph.dcs.stone_age.BoardFactory;
import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

public final class GameBoardFactory extends BoardFactory {
    @Override
    protected InterfaceGetState createBoard() {
        CivilisationCardDeck civilisationCardDeck = new CivilisationCardDeck(new Stack<>(){});
        ArrayList<Building> buildings = new ArrayList<>();
        Player[] players = new Player[2];
        return new GameBoard(civilisationCardDeck, buildings, players);
    }

    public static GameBoard createGameBoard(final int numberOfPlayers) {
        CivilisationCardDeck civilisationCardDeck = new CivilisationCardDeck(new Stack<>(){});
        ArrayList<Building> buildings = new ArrayList<>();
        Player[] players = new Player[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {
            PlayerOrder playerOrder = new PlayerOrder(i, numberOfPlayers);
            PlayerBoardFactory playerBoardFactory = new PlayerBoardFactory();
            PlayerBoard playerBoard = (PlayerBoard) playerBoardFactory.create();
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
