package sk.uniba.fmph.dcs.game_board;

import java.util.OptionalInt;
import sk.uniba.fmph.dcs.stone_age.Effect;

interface Building {
    OptionalInt build(Effect[] resources);
}
