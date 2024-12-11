package sk.uniba.fmph.dcs.game_phase_controller;

import sk.uniba.fmph.dcs.game_board.FigureLocationAdaptor;
import sk.uniba.fmph.dcs.game_board.GameBoard;
import sk.uniba.fmph.dcs.game_board.Player;
import sk.uniba.fmph.dcs.game_board.RewardMenu;
import sk.uniba.fmph.dcs.stone_age.*;

import java.util.HashMap;
import java.util.Map;

public final class GamePhaseControllerFactory {

    public static GamePhaseController createGamePhaseController(GameBoard gameBoard) {
        Map<GamePhase, InterfaceGamePhaseState> gamePhaseStateMap = new HashMap<>();
        Map<Location, InterfaceFigureLocation> figureLocationMap = new HashMap<>();
        for (Location location : Location.values()) {
            figureLocationMap.put(location,
                    new FigureLocationAdaptor(gameBoard.getLocation(location), gameBoard.getPlayers()));
        }
        gamePhaseStateMap.put(GamePhase.PLACE_FIGURES, new PlaceFiguresState(figureLocationMap));
        gamePhaseStateMap.put(GamePhase.MAKE_ACTION, new MakeActionState(figureLocationMap));
        gamePhaseStateMap.put(GamePhase.ALL_PLAYERS_TAKE_A_REWARD,
                new AllPlayersTakeARewardState(new RewardMenu(gameBoard.getPlayers())));
        Map<PlayerOrder, InterfaceFeedTribe> feedTribeMap = new HashMap<>();
        for (Player player : gameBoard.getPlayers()) {
            feedTribeMap.put(player.playerOrder(), (InterfaceFeedTribe) player.playerBoard());
        }
        gamePhaseStateMap.put(GamePhase.FEED_TRIBE, new FeedTribeState(feedTribeMap));
        Map<PlayerOrder, InterfaceToolUse> toolUseMap = new HashMap<>();
        for (Player player : gameBoard.getPlayers()) {
            toolUseMap.put(player.playerOrder(), gameBoard.getCurrentThrow());
        }
        gamePhaseStateMap.put(GamePhase.WAITING_FOR_TOOL_USE, new WaitingForToolUseState(toolUseMap));
        Map<PlayerOrder, InterfaceNewTurn> newTurnMap = new HashMap<>();
        for (Player player : gameBoard.getPlayers()) {
            newTurnMap.put(player.playerOrder(), (InterfaceNewTurn) player.playerBoard());
        }
        gamePhaseStateMap.put(GamePhase.NEW_ROUND,
                new NewRoundState(figureLocationMap.values().toArray(new InterfaceFigureLocation[0]), newTurnMap));
        gamePhaseStateMap.put(GamePhase.GAME_END, new GameEndState());

        return new GamePhaseController(gamePhaseStateMap, gameBoard.getPlayers()[0].playerOrder());
    }
}
