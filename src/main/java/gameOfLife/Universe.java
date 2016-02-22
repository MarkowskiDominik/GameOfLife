package gameOfLife;

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
        gatheringNotifyOfCells();
        enterOfUpdates();
        deleteCellsOfZeroLivingNeighbors();
    }

    private void gatheringNotifyOfCells() {
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
        for (Cell cellChangingState : updates) {
            if (CellState.REVIVAL.equals(cellChangingState.getCellState())) {
                revivalUpdate(cellChangingState);
            }
            if (CellState.DIES.equals(cellChangingState.getCellState())) {
                dyingUpdate(cellChangingState);
            }
        }
        updates.clear();
    }

    private void revivalUpdate(Cell cellChangingState) {
        cellChangingState.setCellState(CellState.LIVING);
        addToCellExistingNeighbors(cellChangingState);
        generateNewNeighbors(cellChangingState);
        cellChangingState.increaseNumberOfLivingNeighborsToCellsOnTheNeighborList();
    }

    private void dyingUpdate(Cell cellChangingState) {
        cellChangingState.setCellState(CellState.NEIGHBOR);
        cellChangingState.decreaseNumberOfLivingNeighborsToCellsOnTheNeighborList();
    }

    private void addToCellExistingNeighbors(Cell cellChangingState) {
        for (Cell cell : cells) {
        	if (cellIsNeighborForCellChangingState(cell, cellChangingState) ) {
        		cellChangingState.addNeighbor(cell);        		
        	}
        }
    }

    private boolean cellIsNeighborForCellChangingState(Cell cell, Cell cellChangingState) {
        int cellChangingStateColumn = cellChangingState.getColumn();
        int cellChangingStateRow = cellChangingState.getRow();
        int cellColumn = cell.getColumn();
        int cellRow = cell.getRow();

		return cellChangingStateRow - 2 < cellRow && cellRow < cellChangingStateRow + 2
				&& cellChangingStateColumn - 2 < cellColumn && cellColumn < cellChangingStateColumn + 2
				&& !(cellRow == cellChangingStateRow && cellColumn == cellChangingStateColumn);
	}

	private void generateNewNeighbors(Cell update) {
        int row = update.getRow();
        int column = update.getColumn();

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
            if (cell.getNumberOfLivingNeighbors() == 0) {
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