package sk.uniba.fmph.dcs.player_board;

import org.junit.jupiter.api.Test;
import sk.uniba.fmph.dcs.stone_age.EndOfGameEffect;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerCivilisationCardsTest {
    @Test
    public void itGivesPointsForGreenCards() {
        PlayerCivilisationCards cards = new PlayerCivilisationCards();

        EndOfGameEffect[] effects = new EndOfGameEffect[] { EndOfGameEffect.MUSIC, EndOfGameEffect.MUSIC,
                EndOfGameEffect.ART }; // 2*2 + 1
        cards.addEndOfGameEffects(effects);
        cards.addEndOfGameEffects(effects);

        assertEquals(10, cards.calculateEndOfGameCivilisationCardPoints(0, 0, 0, 0));
    }

    @Test
    public void itGivesPointsForSandCards() {
        PlayerCivilisationCards cards = new PlayerCivilisationCards();

        EndOfGameEffect[] effects = new EndOfGameEffect[] { EndOfGameEffect.FARMER, EndOfGameEffect.BUILDER,
                EndOfGameEffect.TOOL_MAKER, EndOfGameEffect.SHAMAN };
        cards.addEndOfGameEffects(effects);

        assertEquals(2, cards.calculateEndOfGameCivilisationCardPoints(2, -1, 0, 0));
        assertEquals(2, cards.calculateEndOfGameCivilisationCardPoints(0, 2, 0, 0));
        assertEquals(2, cards.calculateEndOfGameCivilisationCardPoints(0, 0, 2, 0));
        assertEquals(2, cards.calculateEndOfGameCivilisationCardPoints(0, 0, 0, 2));

        cards.addEndOfGameEffects(effects);

        assertEquals(4, cards.calculateEndOfGameCivilisationCardPoints(2, 0, 0, 0));
        assertEquals(4, cards.calculateEndOfGameCivilisationCardPoints(-1, 2, 0, 0));
        assertEquals(4, cards.calculateEndOfGameCivilisationCardPoints(0, 0, 2, 0));
        assertEquals(4, cards.calculateEndOfGameCivilisationCardPoints(0, 0, 0, 2));
    }
}
