package sk.uniba.fmph.dcs.player_board;

import sk.uniba.fmph.dcs.stone_age.InterfaceGetState;

import javax.naming.CannotProceedException;

public final class PlayerBoard implements InterfaceGetState {
    private int points = 0;
    private int houses = 0;

    private final PlayerResourcesAndFood resourcesAndFood;
    private final PlayerFigures figures;
    private final PlayerCivilisationCards civilisationCards;
    private final PlayerTools tools;
    private final TribeFedStatus fedStatus;

    public PlayerBoard(final PlayerResourcesAndFood resourcesAndFood, final PlayerFigures figures,
            final PlayerCivilisationCards civilisationCards, final PlayerTools tools, final TribeFedStatus fedStatus) {
        this.resourcesAndFood = resourcesAndFood;
        this.figures = figures;
        this.civilisationCards = civilisationCards;
        this.tools = tools;
        this.fedStatus = fedStatus;
    }

    public void addPoints(final int points) {
        this.points += points;
    }

    public void addHouse() {
        this.houses++;
    }

    public void newTurn() throws CannotProceedException {
        if (!this.fedStatus.isTribeFed()) {
            throw new CannotProceedException("Cannot start new turn when tribe is not fed");
        }

        tools.newTurn();
        figures.newTurn();
        fedStatus.newTurn();
    }

    public PlayerResourcesAndFood getResourcesAndFood() {
        return resourcesAndFood;
    }

    public PlayerFigures getFigures() {
        return figures;
    }

    public PlayerCivilisationCards getCivilisationCards() {
        return civilisationCards;
    }

    public PlayerTools getTools() {
        return tools;
    }

    public TribeFedStatus getFedStatus() {
        return fedStatus;
    }

    @Override
    public String state() {
        return String.format("%d,%d", points, houses);
    }
}
