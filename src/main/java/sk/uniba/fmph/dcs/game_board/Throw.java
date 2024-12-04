package sk.uniba.fmph.dcs.game_board;

public final class Throw {

    private final int dieFaces = 6;

    public int[] throwDice(final int dice) {
        int[] results = new int[dice];
        for (int i = 0; i < results.length; i++) {
            results[i] = (int) (Math.random() * dieFaces) + 1;
        }
        return results;
    }
}
