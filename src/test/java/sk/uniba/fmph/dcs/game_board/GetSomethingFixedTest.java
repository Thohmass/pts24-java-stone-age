package sk.uniba.fmph.dcs.game_board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.EndOfGameEffect;
import sk.uniba.fmph.dcs.stone_age.InterfacePlayerBoardGameBoard;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetSomethingFixedTest {

    private GetSomethingFixed fixed;
    private Player player;
    private InterfacePlayerBoardGameBoard iFace;
    private Effect choice;
    private Effect[] effects;

    @BeforeEach
    public void setUp() {
        iFace = new InterfacePlayerBoardGameBoard() {
            @Override
            public void giveEffect(Effect[] stuff) {

            }

            @Override
            public void giveEndOfGameEffect(EndOfGameEffect[] stuff) {

            }

            @Override
            public boolean takeResources(Effect[] stuff) {
                return false;
            }

            @Override
            public boolean giveFigure() {
                return false;
            }

            @Override
            public boolean takeFigures(int count) {
                return false;
            }

            @Override
            public boolean hasFigures(int count) {
                return false;
            }

            @Override
            public boolean hasSufficientTools(int goal) {
                return false;
            }

            @Override
            public Optional<Integer> useTool(int idx) {
                return Optional.empty();
            }
        };
        player = new Player(new PlayerOrder(0, 0), iFace);
        choice = Effect.WOOD;
        effects = new Effect[2];
        effects[0] = Effect.TOOL;
        effects[1] = Effect.ONE_TIME_TOOL2;
        fixed = new GetSomethingFixed(effects);
    }

    @Test
    public void correctAnswer() {
        boolean result = fixed.performEffect(player, choice);
        assertEquals(result, true);
    }
}
