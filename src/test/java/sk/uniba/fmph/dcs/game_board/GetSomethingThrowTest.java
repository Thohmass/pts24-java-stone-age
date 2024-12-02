package sk.uniba.fmph.dcs.game_board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.EndOfGameEffect;
import sk.uniba.fmph.dcs.stone_age.InterfacePlayerBoardGameBoard;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class GetSomethingThrowTest {

    private GetSomethingThrow getSomethingThrow;
    private CurrentThrow currentThrow;
    private Effect resource;
    private Player player;
    private Effect choice;

    @BeforeEach
    public void setUp() {
        InterfacePlayerBoardGameBoard iFace = new InterfacePlayerBoardGameBoard() {
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
        resource = Effect.WOOD;
        choice = Effect.WOOD;
        currentThrow = new CurrentThrow(Effect.WOOD, 2);
        getSomethingThrow = new GetSomethingThrow(resource, currentThrow);
    }

    @Test
    public void performsEffect() {
        boolean result = getSomethingThrow.performEffect(player, choice);
        assertEquals(true, result);
    }

    @Test
    public void wrongResource() {
        choice = Effect.CLAY;
        boolean result = getSomethingThrow.performEffect(player, choice);
        assertEquals(false, result);
    }
}
