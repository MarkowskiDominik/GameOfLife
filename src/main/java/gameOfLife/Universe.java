package src.main.java.gameOfLife;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.TreeSet;

public class Universe implements Observer {

    private Set<Cell> cells;
    private Set<Cell> updates;

    public Universe() {
        cells = new TreeSet<Cell>();
        updates = new TreeSet<Cell>();
    }

    public void symulationOfCellsLife() {
        requestStatusOfCells();
        enterOfUpdates();
        deleteCellsOfZeroLivingNeighbors();
    }

    // REVIEW dmarkowski - there is no status returned here, the method is named inaccurately
    private void requestStatusOfCells() {
        for (Cell cell : cells) {
            cell.notifyObservers();
        }
    }

    @Override
    public void update(Observable cell, Object cellState) {
        updates.add((Cell) cell);
    }

    public void initializeCells(Set<Cell> cells) {
        this.cells = new TreeSet<Cell>(cells);
        this.updates = new TreeSet<Cell>(cells);
        enterOfUpdates();
    }

    private void enterOfUpdates() {
        // REVIEW dmarkowski - wrong name for Cell 'update'
        for (Cell update : updates) {
            if (update.getCellState() == CellState.REVIVAL) {
                revivalUpdate(update);
            }
            if (update.getCellState() == CellState.DIES) {
                dyingUpdate(update);
            }
        }
        updates.clear();
    }

    private void revivalUpdate(Cell update) {
        update.setCellState(CellState.LIVING);
        addToCellExistingNeighbors(update);
        generateNewNeighbors(update);
        update.increaseCountersNeighbors();
    }

    private void dyingUpdate(Cell update) {
        update.setCellState(CellState.NEIGHBOR);
        update.decreaseCountersNeighbors();
    }

    private void addToCellExistingNeighbors(Cell update) {
        Integer updateColumn = update.getColumn();
        Integer updateRow = update.getRow();

        for (Cell neighbor : cells) {
            // REVIEW dmarkowski - there is no win in using Integer here, you neither use compare nor checks for nulls
            Integer neighborColumn = neighbor.getColumn();
            Integer neighborRow = neighbor.getRow();
            // REVIEW dmarkowski - please avoid such long conditions, instead extract them to a method with name. Then
            // this method name works as a comment.
            if (updateRow - 2 < neighborRow && neighborRow < updateRow + 2 && updateColumn - 2 < neighborColumn && neighborColumn < updateColumn + 2 && !(neighborRow == updateRow && neighborColumn == updateColumn)) {
                update.addNeighbor(neighbor);
            }
        }
    }

    private void generateNewNeighbors(Cell update) {
        Integer row = update.getRow();
        Integer column = update.getColumn();

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Cell neighbor = new Cell(column + i, row + j, CellState.NEIGHBOR, this);
                if (!cells.contains(neighbor)) {
                    cells.add(neighbor);
                    update.addNeighbor(neighbor);
                }
            }
        }
    }

    private void deleteCellsOfZeroLivingNeighbors() {
        TreeSet<Cell> toRemove = new TreeSet<Cell>();
        for (Cell cell : cells) {
            if (cell.getNumberOfLivingNeighbors().equals(Integer.valueOf(0))) {
                cell.requestOfRemoveFromNeighbors();
                toRemove.add(cell);
            }
        }
        cells.removeAll(toRemove);
    }

    public Set<Cell> getCells() {
        return cells;
    }
}