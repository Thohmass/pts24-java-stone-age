package sk.uniba.fmph.dcs.player_board;

import org.junit.jupiter.api.Test;

import javax.naming.LimitExceededException;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFiguresTest {
    @Test
    void itAddsFigures() throws LimitExceededException {
        PlayerFigures figures = new PlayerFigures();

        figures.addNewFigure();

        assertEquals(6, figures.getTotalFigures());
    }

    @Test
    void itHasMaxFigures() {
        PlayerFigures figures = new PlayerFigures();

        assertTrue(figures.hasFigures(5));
    }

    @Test
    void itHasMinFigures() {
        PlayerFigures figures = new PlayerFigures();

        assertTrue(figures.hasFigures(0));
    }

    @Test
    void itHasAnyFigures() {
        PlayerFigures figures = new PlayerFigures();

        assertFalse(figures.hasFigures(-1));
        assertFalse(figures.hasFigures(6));

        assertTrue(figures.hasFigures(4));
    }

    @Test
    void itTakesFigures() {
        PlayerFigures figures = new PlayerFigures();

        assertTrue(figures.takeFigures(2));
        assertTrue(figures.takeFigures(3));
    }

    @Test
    void itDoesNotTakesFiguresIfNotEnoughFigures() {
        PlayerFigures figures = new PlayerFigures();

        assertFalse(figures.takeFigures(6));
    }

    @Test
    void newTurnFigureCountCorrect() throws LimitExceededException {
        PlayerFigures figures = new PlayerFigures();

        figures.addNewFigure();

        figures.takeFigures(6);

        figures.newTurn();

        assertEquals(6, figures.getTotalFigures());
    }

    @Test
    void takesNewFigureOnlyOnce() throws LimitExceededException {
        PlayerFigures figures = new PlayerFigures();

        figures.addNewFigure();
        figures.addNewFigure();
        assertEquals(6, figures.getTotalFigures());
    }

    @Test
    void maximumFiguresReached() throws LimitExceededException {
        PlayerFigures figures = new PlayerFigures();

        figures.addNewFigure();

        figures.newTurn();
        figures.addNewFigure();
        figures.newTurn();
        figures.addNewFigure();
        figures.newTurn();
        figures.addNewFigure();
        figures.newTurn();
        figures.addNewFigure();
        figures.newTurn();
        assertThrows(LimitExceededException.class, figures::addNewFigure);
    }
}