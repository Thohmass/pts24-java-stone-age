package sk.uniba.fmph.dcs.stone_age;

import sk.uniba.fmph.dcs.game_phase_controller.GamePhaseController;

import java.util.Collection;
import java.util.Map;

public final class StoneAgeGame implements InterfaceStoneAgeGame {

    private final Map<Integer, PlayerOrder> players;

    private final GamePhaseController gamePhaseController;
    private final InterfaceGetState gameBoardState;
    private final InterfaceGetState playerBoardState;

    private final StoneAgeObservable stoneAgeObservable;

    private void notifyObserver() {
        stoneAgeObservable.notify(gameBoardState.state());
        stoneAgeObservable.notify(playerBoardState.state());
        stoneAgeObservable.notify(gamePhaseController.state());
    }

    public StoneAgeGame(final Map<Integer, PlayerOrder> players, final GamePhaseController gamePhaseController,
            final InterfaceGetState gameBoardState, final InterfaceGetState playerBoardState,
            final StoneAgeObservable stoneAgeObservable) {
        this.players = players;
        this.gamePhaseController = gamePhaseController;
        this.gameBoardState = gameBoardState;
        this.playerBoardState = playerBoardState;
        this.stoneAgeObservable = stoneAgeObservable;
    }

    @Override
    public boolean placeFigures(final int playerId, final Location location, final int figuresCount) {
        if (players.containsKey(playerId)) {
            boolean ret = gamePhaseController.placeFigures(players.get(playerId), location, figuresCount);
            notifyObserver();
            return ret;
        }
        return false;
    }

    @Override
    public boolean makeAction(final int playerId, final Location location, final Collection<Effect> usedResources,
            final Collection<Effect> desiredResources) {
        if (players.containsKey(playerId)) {
            boolean ret = gamePhaseController.makeAction(players.get(playerId), location, usedResources,
                    desiredResources);
            notifyObserver();
            return ret;
        }
        return false;
    }

    @Override
    public boolean skipAction(final int playerId, final Location location) {
        if (players.containsKey(playerId)) {
            boolean ret = gamePhaseController.skipAction(players.get(playerId), location);
            notifyObserver();
            return ret;
        }
        return false;
    }

    @Override
    public boolean useTools(final int playerId, final int toolIndex) {
        if (players.containsKey(playerId)) {
            boolean ret = gamePhaseController.useTools(players.get(playerId), toolIndex);
            notifyObserver();
            return ret;
        }
        return false;
    }

    @Override
    public boolean noMoreToolsThisThrow(final int playerId) {
        if (players.containsKey(playerId)) {
            boolean ret = gamePhaseController.noMoreToolsThisThrow(players.get(playerId));
            notifyObserver();
            return ret;
        }
        return false;
    }

    @Override
    public boolean feedTribe(final int playerId, final Collection<Effect> resources) {
        if (players.containsKey(playerId)) {
            boolean ret = gamePhaseController.feedTribe(players.get(playerId), resources);
            notifyObserver();
            return ret;
        }
        return false;
    }

    @Override
    public boolean doNotFeedThisTurn(final int playerId) {
        if (players.containsKey(playerId)) {
            boolean ret = gamePhaseController.doNotFeedThisTurn(players.get(playerId));
            notifyObserver();
            return ret;
        }
        return false;
    }

    @Override
    public boolean makeAllPlayerTakeARewardChoice(final int playerId, final Effect rewardIndex) {
        if (players.containsKey(playerId)) {
            boolean ret = gamePhaseController.makeAllPlayersTakeARewardChoice(players.get(playerId), rewardIndex);
            notifyObserver();
            return ret;
        }
        return false;
    }
}
