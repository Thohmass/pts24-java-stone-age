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
    public void testMakeAllPlayersTakeARewardChoice() {
        PlayerOrder player = new PlayerOrder(1, 1);

        // Test case: Reward is successfully taken
        AllPlayersTakeARewardState state = new AllPlayersTakeARewardState(
                new MockTakeReward(true, HasAction.AUTOMATIC_ACTION_DONE));
        assertEquals(ActionResult.ACTION_DONE, state.makeAllPlayersTakeARewardChoice(player, Effect.WOOD));

        // Test case: Reward is not successfully taken
        state = new AllPlayersTakeARewardState(new MockTakeReward(false, HasAction.WAITING_FOR_PLAYER_ACTION));
        assertEquals(ActionResult.FAILURE, state.makeAllPlayersTakeARewardChoice(player, Effect.WOOD));
    }

    @Test
    public void testTryToMakeAutomaticAction() {
        PlayerOrder player = new PlayerOrder(1, 1);

        // Test case: No action possible
        AllPlayersTakeARewardState state = new AllPlayersTakeARewardState(
                new MockTakeReward(false, HasAction.NO_ACTION_POSSIBLE));
        assertEquals(HasAction.NO_ACTION_POSSIBLE, state.tryToMakeAutomaticAction(player));

        // Test case: Automatic action done
        state = new AllPlayersTakeARewardState(new MockTakeReward(true, HasAction.AUTOMATIC_ACTION_DONE));
        assertEquals(HasAction.AUTOMATIC_ACTION_DONE, state.tryToMakeAutomaticAction(player));

        // Test case: Waiting for player action
        state = new AllPlayersTakeARewardState(new MockTakeReward(false, HasAction.WAITING_FOR_PLAYER_ACTION));
        assertEquals(HasAction.WAITING_FOR_PLAYER_ACTION, state.tryToMakeAutomaticAction(player));
    }
}
