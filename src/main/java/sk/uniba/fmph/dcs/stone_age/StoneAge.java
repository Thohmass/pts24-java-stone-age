package sk.uniba.fmph.dcs.stone_age;

import sk.uniba.fmph.dcs.game_board.GameBoard;
import sk.uniba.fmph.dcs.game_board.GameBoardFactory;

public class StoneAge {
    private final GameBoard gameBoard;
    private final StoneAgeGame stoneAgeGame;

    public StoneAge(final int numberOfPlayers) {
        gameBoard = GameBoardFactory.createGameBoard(numberOfPlayers);
        stoneAgeGame = StoneAgeGameFactory.createStoneAgeGame(numberOfPlayers);
    }


}
