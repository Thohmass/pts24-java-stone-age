package sk.uniba.fmph.dcs.stone_age;

import sk.uniba.fmph.dcs.game_board.GameBoard;
import sk.uniba.fmph.dcs.game_board.GameBoardFactory;

import java.io.IOException;

public class StoneAge {
    private final StoneAgeGame stoneAgeGame;

    public StoneAge(final int numberOfPlayers) throws IOException {
        stoneAgeGame = StoneAgeGameFactory.createStoneAgeGame(numberOfPlayers);
    }


}
