package sk.uniba.fmph.dcs.stone_age;

import sk.uniba.fmph.dcs.game_board.*;
import sk.uniba.fmph.dcs.game_phase_controller.*;
import sk.uniba.fmph.dcs.player_board.PlayerBoardGameBoardFacade;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class StoneAgeGameFactory {

    public static StoneAgeGame createStoneAgeGame(final int numberOfPlayers) throws IOException {
        GameBoard gameBoard = GameBoardFactory.createGameBoard(numberOfPlayers);

        Map<Integer, PlayerOrder> playerOrderMap = new HashMap<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            playerOrderMap.put(i, new PlayerOrder(i, numberOfPlayers));
        }

        GamePhaseController gamePhaseController = GamePhaseControllerFactory.createGamePhaseController(gameBoard);

        PlayerBoardGameBoardFacade playerBoardGameBoardFacade
                = (PlayerBoardGameBoardFacade) gameBoard.getPlayers()[0].playerBoard();

        StoneAgeObservable stoneAgeObservable = new StoneAgeObservable();

        return new StoneAgeGame(playerOrderMap, gamePhaseController, gameBoard,
                playerBoardGameBoardFacade.getPlayerBoard(), stoneAgeObservable);
    }
}
