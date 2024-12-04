package sk.uniba.fmph.dcs.game_board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.EndOfGameEffect;
import sk.uniba.fmph.dcs.stone_age.InterfacePlayerBoardGameBoard;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetChoiceTest {

    private Player player;
    private Effect effect;
    private Effect[] numberOfResources;
    private GetChoice choice;

    @BeforeEach
    public void setUp() {
        InterfacePlayerBoardGameBoard interfacePlayerBoardGameBoard = new InterfacePlayerBoardGameBoard() {
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
        player = new Player(new PlayerOrder(0, 0), interfacePlayerBoardGameBoard);
        effect = Effect.CLAY;
        numberOfResources = new Effect[2];
        numberOfResources[0] = Effect.WOOD;
        numberOfResources[1] = Effect.TOOL;
        choice = new GetChoice(numberOfResources);
    }

    @Test
    public void correctAnswer() {
        boolean result = choice.performEffect(player, effect);
        assertEquals(result, true);
    }

    @Test
    public void wrongAnswer() {
        effect = Effect.FOOD;
        boolean result = choice.performEffect(player, effect);
        assertEquals(result, false);
    }

}
