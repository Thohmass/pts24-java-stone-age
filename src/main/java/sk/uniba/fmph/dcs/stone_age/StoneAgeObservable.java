package sk.uniba.fmph.dcs.stone_age;

import java.util.HashMap;
import java.util.Map;

public final class StoneAgeObservable implements InterfaceStoneAgeObservable {
    private final Map<Integer, InterfaceStoneAgeObserver> observers;

    public StoneAgeObservable() {
        this.observers = new HashMap<>();
    }

    @Override
    public void registerObserver(final int playerId, final InterfaceStoneAgeObserver observer) {
        observers.put(playerId, observer);
    }

    public void notify(final String gameState) {
        for (InterfaceStoneAgeObserver observer : observers.values()) {
            observer.update(gameState);
        }
    }
}
