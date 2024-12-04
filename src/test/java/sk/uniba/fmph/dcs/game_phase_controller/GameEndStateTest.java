package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.jupiter.api.Test;
import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.Location;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameEndStateTest {
    @Test
    public void testTryToMakeAutomaticAction() {
        GameEndState gameEndState = new GameEndState();
        PlayerOrder player = new PlayerOrder(1, 1);
        assertEquals(gameEndState.tryToMakeAutomaticAction(player), HasAction.WAITING_FOR_PLAYER_ACTION);
    }

    @Test
    public void testMethodsReturnActionResultFailure() {
        PlayerOrder player = new PlayerOrder(1, 1);
        Effect[] resources = new Effect[] {};
        GameEndState gameEndState = new GameEndState();

        assertEquals(gameEndState.doNotFeedThisTurn(player), ActionResult.FAILURE);
        assertEquals(gameEndState.feedTribe(player, resources), ActionResult.FAILURE);
        assertEquals(gameEndState.makeAction(player, Location.BUILDING_TILE1, resources, resources),
                ActionResult.FAILURE);
        assertEquals(gameEndState.skipAction(player, Location.BUILDING_TILE1), ActionResult.FAILURE);
        assertEquals(gameEndState.makeAllPlayersTakeARewardChoice(player, Effect.WOOD), ActionResult.FAILURE);
        assertEquals(gameEndState.placeFigures(player, Location.BUILDING_TILE1, 1), ActionResult.FAILURE);
        assertEquals(gameEndState.noMoreToolsThisThrow(player), ActionResult.FAILURE);
        assertEquals(gameEndState.useTools(player, 0), ActionResult.FAILURE);
    }
}
