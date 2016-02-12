package gameOfLife;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class Universe implements Observer {

	private LinkedList<Cell> cells;
	private LinkedList<Cell> updates;

	public Universe() {
		cells = new LinkedList<Cell>();
		updates = new LinkedList<Cell>();
	}

	public void symulationOfCellsLife() {
		requestStatusOfCells();
		enterOfUpdates();
	}

	private void requestStatusOfCells() {
		for (Cell cell : cells) {
			cell.notifyObservers();
		}
	}

	public void update(Observable cell, Object cellState) {
		updates.add((Cell) cell);
	}

	private void enterOfUpdates() {
		for (Cell update : updates) {
			if (update.getCellState() == CellState.REVIVAL) {
				revivalUpdate(update);
			}
			if (update.getCellState() == CellState.DYING) {
				dyingUpdate(update);
			}
		}
		updates.clear();
		deleteCellsOfZeroLivingNeighbors();
	}

	private void revivalUpdate(Cell update) {
		update.setCellState(CellState.LIVING);
		addNewNeighbors(update);
		changeNumberOfLivingNeighbors(update, 1);
	}

	private void dyingUpdate(Cell update) {
		update.setCellState(CellState.NEIGHBOR);
		changeNumberOfLivingNeighbors(update, -1);
	}

	private void changeNumberOfLivingNeighbors(Cell update, int change) {
		Integer cellColumn = update.getColumn();
		Integer cellRow = update.getRow();

		for (Cell cell : cells) {
			Integer updateRow = cell.getRow();
			Integer updateColumn = cell.getColumn();
			if (cellRow - 2 < updateRow && updateRow < cellRow + 2 && cellColumn - 2 < updateColumn
					&& updateColumn < cellColumn + 2 && !(updateRow == cellRow && updateColumn == cellColumn)) {
				cell.setNumberOfLivingNeighbors(cell.getNumberOfLivingNeighbors() + change);
			}
		}
	}

	private void addNewNeighbors(Cell update) {
		Integer row = update.getRow();
		Integer column = update.getColumn();

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				Cell neighbor = new Cell(column + i, row + j, CellState.NEIGHBOR, this);
				if (!cells.contains(neighbor)) {
					cells.add(neighbor);
				}
			}
		}
	}

	private void deleteCellsOfZeroLivingNeighbors() {
		LinkedList<Cell> toRemove = new LinkedList<Cell>();
		for (Cell cell : cells) {
			if (cell.getNumberOfLivingNeighbors().equals(Integer.valueOf(0))) {
				toRemove.add(cell);
			}
		}
		cells.removeAll(toRemove);
	}

	public LinkedList<Cell> getCells() {
		return cells;
	}

	public void setCells(LinkedList<Cell> cells) {
		this.cells = new LinkedList<Cell>(cells);
	}

}
