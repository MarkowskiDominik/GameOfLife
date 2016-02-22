package gameOfLife;

import java.util.Set;
import java.util.TreeSet;

// REVIEW dmarkowski - general issue - missing coments for classes and public methods
public class GameOfLife {

    private final Universe universe;
    private Set<Cell> cells;

    public GameOfLife(Set<Cell> cells) {
        this.cells = new TreeSet<Cell>(cells);
        universe = new Universe();

        setUniverseAsObserverToCell();
        universe.initializeCells(cells);
    }

    private void setUniverseAsObserverToCell() {
        for (Cell cell : cells) {
            cell.setObserver(universe);
        }
    }

    public void symulationOfCellsLife() {
        universe.symulationOfCellsLife();
    }

    public TreeSet<Cell> getLivingCells() {
        cells = universe.getCells();
        TreeSet<Cell> livingCells = new TreeSet<Cell>();
        for (Cell cell : cells) {
            if (cell.getCellState() == CellState.LIVING) {
                livingCells.add(cell);
            }
        }
        return livingCells;
    }
}
