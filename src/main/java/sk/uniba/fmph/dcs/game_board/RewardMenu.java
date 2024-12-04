package sk.uniba.fmph.dcs.game_board;

import org.apache.commons.lang3.ArrayUtils;
import sk.uniba.fmph.dcs.stone_age.Effect;
import sk.uniba.fmph.dcs.stone_age.HasAction;
import sk.uniba.fmph.dcs.stone_age.PlayerOrder;
import sk.uniba.fmph.dcs.stone_age.InterfaceTakeReward;

import java.util.Arrays;

public final class RewardMenu implements InterfaceTakeReward {

    private Effect[] menu;
    private final Player[] players;

    public RewardMenu(final Effect[] menu, final Player[] players) {
        this.menu = menu;
        this.players = players;
    }

    public void initiate(final Effect[] menu) {
        this.menu = menu;
    }

    private Player getPlayer(final PlayerOrder playerOrder) {
        Player player = null;
        for (Player p : players) {
            if (p.playerOrder().equals(playerOrder)) {
                player = p;
            }
        }
        return player;
    }

    @Override
    public Boolean takeReward(final PlayerOrder player, final Effect reward) {
        if (Arrays.stream(menu).noneMatch(effect -> effect == reward)) {
            return false;
        }
        Player p = getPlayer(player);
        if (p == null) {
            return false;
        }
        menu = ArrayUtils.removeElement(menu, reward);
        p.playerBoard().giveEffect(new Effect[] { reward });
        return true;
    }

    @Override
    public HasAction tryMakeAction(final PlayerOrder player) {
        if (menu.length == 0) {
            return HasAction.NO_ACTION_POSSIBLE;
        }
        if (menu.length == 1) {
            Player p = getPlayer(player);
            p.playerBoard().giveEffect(menu);
            return HasAction.AUTOMATIC_ACTION_DONE;
        }
        return HasAction.WAITING_FOR_PLAYER_ACTION;
    }

    public String state() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Menu: ");
        String prefix = "";
        for (Effect e : menu) {
            stringBuilder.append(prefix);
            prefix = ", ";
            stringBuilder.append(e);
        }
        stringBuilder.append("\nPlayers: ");
        prefix = "";
        for (Player p : players) {
            stringBuilder.append(prefix);
            prefix = ", ";
            stringBuilder.append(p);
        }
        return stringBuilder.toString();
    }
}
