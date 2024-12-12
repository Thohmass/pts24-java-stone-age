package sk.uniba.fmph.dcs.stone_age;

import java.io.IOException;

public class StoneAge {
    private final StoneAgeGame stoneAgeGame;

    public StoneAge(final int numberOfPlayers) throws IOException {
        stoneAgeGame = StoneAgeGameFactory.createStoneAgeGame(numberOfPlayers);
    }


}
