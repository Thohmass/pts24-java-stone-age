package sk.uniba.fmph.dcs.game_phase_controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import sk.uniba.fmph.dcs.stone_age.ActionResult;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.InterfaceTakeReward;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;

public class AllPlayersTakeARewardStateTest {

    private static class MockTakeReward implements InterfaceTakeReward {
        private boolean returnValue;
        private HasAction hasAction;

        public MockTakeReward(boolean returnValue, HasAction hasAction) {
            this.returnValue = returnValue;
            this.hasAction = hasAction;
        }

        @Override
        public Boolean takeReward(PlayerOrder player, Effect reward) {
            return returnValue;
        }

        @Override
        public HasAction tryMakeAction(PlayerOrder player) {
            return hasAction;
        }
    }

    @Test
    public void testMakeAllPlayersTakeARewardChoiceAutomaticActionDone() {
        PlayerOrder player = new PlayerOrder(1, 1);

        AllPlayersTakeARewardState state = new AllPlayersTakeARewardState(
                new MockTakeReward(true, HasAction.AUTOMATIC_ACTION_DONE));
        assertEquals(ActionResult.ACTION_DONE, state.makeAllPlayersTakeARewardChoice(player, Effect.WOOD));

    }

    @Test
    public void testMakeAllPlayersTakeARewardChoiceWaitingForPlayerAction() {
        PlayerOrder player = new PlayerOrder(1, 1);

        AllPlayersTakeARewardState state = new AllPlayersTakeARewardState(
                new MockTakeReward(false, HasAction.WAITING_FOR_PLAYER_ACTION));
        assertEquals(ActionResult.FAILURE, state.makeAllPlayersTakeARewardChoice(player, Effect.WOOD));
    }

    @Test
    public void testTryToMakeAutomaticActionNoActionPossible() {
        PlayerOrder player = new PlayerOrder(1, 1);

        AllPlayersTakeARewardState state = new AllPlayersTakeARewardState(
                new MockTakeReward(false, HasAction.NO_ACTION_POSSIBLE));
        assertEquals(HasAction.NO_ACTION_POSSIBLE, state.tryToMakeAutomaticAction(player));
    }

    @Test
    public void testTryToMakeAutomaticActionFailAutomaticActionDone() {
        PlayerOrder player = new PlayerOrder(1, 1);
        AllPlayersTakeARewardState state = new AllPlayersTakeARewardState(
                new MockTakeReward(true, HasAction.AUTOMATIC_ACTION_DONE));
        assertEquals(HasAction.AUTOMATIC_ACTION_DONE, state.tryToMakeAutomaticAction(player));
    }

    @Test
    public void testTryToMakeAutomaticActionFailWaitingForPlayerActionDone() {
        PlayerOrder player = new PlayerOrder(1, 1);
        AllPlayersTakeARewardState state = new AllPlayersTakeARewardState(
                new MockTakeReward(false, HasAction.WAITING_FOR_PLAYER_ACTION));
        assertEquals(HasAction.WAITING_FOR_PLAYER_ACTION, state.tryToMakeAutomaticAction(player));
    }

}
