package sk.uniba.fmph.dcs.player_board;

import org.junit.jupiter.api.Test;

import javax.naming.CannotProceedException;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerBoardTest {


    @Test
    public void testItIsNotPossibleToStartNewTurnWhenTribeIsNotFed() {
        PlayerBoard board = new PlayerBoard(new PlayerResourcesAndFood(), new PlayerFigures(),
                new PlayerCivilisationCards(), new PlayerTools(), new TribeFedStatus());

        assertThrows(CannotProceedException.class, board::newTurn);
    }

    @Test
    void testAddPoints() {
        PlayerBoard board = new PlayerBoard(new PlayerResourcesAndFood(), new PlayerFigures(),
                new PlayerCivilisationCards(), new PlayerTools(), new TribeFedStatus());

        board.addPoints(10);
        assertEquals("10,0", board.state());

        board.addPoints(5);
        assertEquals("15,0", board.state());
    }

    @Test
    void testAddHouse() {
        PlayerBoard board = new PlayerBoard(new PlayerResourcesAndFood(), new PlayerFigures(),
                new PlayerCivilisationCards(), new PlayerTools(), new TribeFedStatus());

        board.addHouse();
        assertEquals("0,1", board.state());

        board.addHouse();
        assertEquals("0,2", board.state());
    }

}
