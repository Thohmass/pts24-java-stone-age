package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.InterfaceFeedTribe;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

import java.util.HashMap;

public class FeedTribeStateTest {

    private static class InterfaceFeedTribeTry implements InterfaceFeedTribe {
        private final int figures;
        private final int food;
        private final boolean returnValue;

        InterfaceFeedTribeTry(int figures, int food, boolean returnValue) {
            this.figures = figures;
            this.food = food;
            this.returnValue = returnValue;
        }

        @Override
        public boolean feedTribeIfEnoughFood() {
            return food >= figures;
        }

        @Override
        public boolean feedTribe(Effect[] resources) {
            return returnValue;
        }

        @Override
        public boolean doNotFeedThisTurn() {
            return returnValue;
        }

        @Override
        public boolean isTribeFed() {
            return returnValue;
        }
    }

    @Test
    public void testTryToMakeAutomaticAction() {
        HashMap<PlayerOrder, InterfaceFeedTribe> map = new HashMap<>();
        PlayerOrder player1 = new PlayerOrder(1, 1);
        PlayerOrder player2 = new PlayerOrder(2, 1);
        PlayerOrder player3 = new PlayerOrder(3, 1);

        map.put(player1, new InterfaceFeedTribeTry(2, 1, false));
        map.put(player2, new InterfaceFeedTribeTry(2, 1, true));
        map.put(player3, new InterfaceFeedTribeTry(1, 2, false));

        FeedTribeState feedState = new FeedTribeState(map);
        assertEquals(feedState.tryToMakeAutomaticAction(player1), HasAction.WAITING_FOR_PLAYER_ACTION);
        assertEquals(feedState.tryToMakeAutomaticAction(player2), HasAction.NO_ACTION_POSSIBLE);
        assertEquals(feedState.tryToMakeAutomaticAction(player3), HasAction.AUTOMATIC_ACTION_DONE);

    }

    @Test
    public void testFeedTribe() {
        HashMap<PlayerOrder, InterfaceFeedTribe> map = new HashMap<>();
        PlayerOrder player1 = new PlayerOrder(1, 1);
        PlayerOrder player2 = new PlayerOrder(2, 1);

        map.put(player1, new InterfaceFeedTribeTry(1, 1, false));
        map.put(player2, new InterfaceFeedTribeTry(1, 1, true));
        Effect[] resources = new Effect[] {};

        FeedTribeState feedState = new FeedTribeState(map);

        assertEquals(feedState.feedTribe(player1, resources), ActionResult.FAILURE);
        assertEquals(feedState.feedTribe(player2, resources), ActionResult.ACTION_DONE);
    }

    @Test
    public void testDoNotFeedThisTurn() {
        HashMap<PlayerOrder, InterfaceFeedTribe> map = new HashMap<>();
        PlayerOrder player1 = new PlayerOrder(1, 1);
        PlayerOrder player2 = new PlayerOrder(2, 1);
        FeedTribeState feedState = new FeedTribeState(map);

        map.put(player1, new InterfaceFeedTribeTry(1, 1, false));
        map.put(player2, new InterfaceFeedTribeTry(1, 1, true));

        assertEquals(feedState.doNotFeedThisTurn(player1), ActionResult.FAILURE);
        assertEquals(feedState.doNotFeedThisTurn(player2), ActionResult.ACTION_DONE);
    }
}