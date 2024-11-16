package sk.uniba.fmph.dcs.stone_age;


import java.util.Optional;

public interface Building {
   Optional<Integer> build(Effect[] resources);
   String state();
}
