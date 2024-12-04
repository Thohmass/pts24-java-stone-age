package sk.uniba.fmph.dcs.stone_age;

public interface InterfaceGamePhaseController {
    boolean placeFigures(PlayerOrder player, Location location, int figuresCount);

    boolean makeAction(PlayerOrder player, Location location, Effect[] inputResources, Effect[] outputResources);

    boolean skipAction(PlayerOrder player, Location location);

    boolean useTools(PlayerOrder player, int toolIndex);

    boolean noMoreToolsThisThrow(PlayerOrder player);

    boolean feedTribe(PlayerOrder player, Effect[] resources);

    boolean doNotFeedThisTurn(PlayerOrder player);

    boolean makeAllPlayersTakeARewardChoice(PlayerOrder player, Effect reward);

    String state();
}
