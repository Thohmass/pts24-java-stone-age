package sk.uniba.fmph.dcs.stone_age;

public interface InterfaceStoneAgeGame {
   boolean placeFigures(int playerId, Location location, int figuresCount);
  boolean  makeAction(int playerId, Location location, Effect[] usedResources);
  boolean skipAction(int playerId, Location location);
  boolean useTools(int playerId, int toolIndex);
  boolean noMoreToolsThisThrow(int playerId);
  boolean feedTribe(int playerId, Effect[] resources);
  boolean doNotFeedThisTurn(int playerId);
  boolean makeAllPlayerTakeARewardChoice(int playerId, Effect rewardIndex);

}
