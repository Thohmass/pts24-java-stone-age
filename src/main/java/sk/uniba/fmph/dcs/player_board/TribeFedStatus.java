package sk.uniba.fmph.dcs.player_board;

import sk.uniba.fmph.dcs.stone_age.Effect;

import java.util.Collection;

public final class TribeFedStatus {
    public void newTurn() {

    }

    public boolean feedTribeIfEnoughFood() {
        return false;
    }

    public boolean feedTribe(final Collection<Effect> resources) {
        return false;
    }

    public boolean isTribeFed() {
        return false;
    }
}
