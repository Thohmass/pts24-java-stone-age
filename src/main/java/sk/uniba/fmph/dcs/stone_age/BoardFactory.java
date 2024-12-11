package sk.uniba.fmph.dcs.stone_age;

public abstract class BoardFactory {
    public InterfaceGetState create() {
        InterfaceGetState board = createBoard();
        return board;
    }
    protected abstract InterfaceGetState createBoard();
}
