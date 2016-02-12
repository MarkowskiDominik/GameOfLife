package gameOfLife;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class Universe implements Observer {
	
	private LinkedList<Cell> livingCells;
	private LinkedList<Cell> neighbors;
	private LinkedList<Cell> dyingCells;
	private LinkedList<Cell> revivalCells;
	private LinkedList<Cell> deleteCells;
	
	public Universe(LinkedList<Cell> livingCells) {
		this.livingCells = new LinkedList<Cell>(livingCells);
		neighbors = new LinkedList<Cell>();
		generateNeighborhoods();
		calculateNumberOfLivingNeighbors();
		dyingCells = new LinkedList<Cell>();
		revivalCells = new LinkedList<Cell>();
		deleteCells = new LinkedList<Cell>();
	}

	private void generateNeighborhoods() {
		for (Cell cell : livingCells) {
			addNeighborsOfCell(cell);
		}
	}

	private void addNeighborsOfCell(Cell cell) {
		Integer row = cell.getRow();
		Integer column = cell.getColumn();

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				Cell neighbor = new Cell(column + i, row + j, CellState.NEIGHBOR);
				if (!livingCells.contains(neighbor) && !neighbors.contains(neighbor)) {
					neighbors.add(neighbor);
				}
			}
		}
	}
	
	private void calculateNumberOfLivingNeighbors() {
		for (Cell cell : livingCells) {
			calculateNumberOfLivingNeighborsForCell(cell);
		}
		for (Cell cell : neighbors) {
			calculateNumberOfLivingNeighborsForCell(cell);
		}
	}

	private void calculateNumberOfLivingNeighborsForCell(Cell cell) {
		Integer numberOfLivingNeighbors = 0;
		Integer cellColumn = cell.getColumn();
		Integer cellRow = cell.getRow();

		for (Cell neighbor : livingCells) {
			Integer neighborColumn = neighbor.getColumn();
			Integer neighborRow = neighbor.getRow();
			if (cellRow - 2 < neighborRow && neighborRow < cellRow + 2 && cellColumn - 2 < neighborColumn
					&& neighborColumn < cellColumn + 2 && !(neighborRow == cellRow && neighborColumn == cellColumn)) {
				numberOfLivingNeighbors++;
			}
		}
		cell.setNumberOfLivingNeighbors(numberOfLivingNeighbors);
	}

	public void symulationOfCellsLife() {
		requestStatusOfCells();
		enterOfUpdates();
	}

	private void requestStatusOfCells() {
		for (Cell cell : livingCells) {
			cell.notifyObservers();
		}
	}

	public void update(Observable cell, Object cellState) {
		if (cellState == CellState.DYING) {
			dyingCells.add((Cell) cell);
		}
		if (cellState == CellState.REVIVAL) {
			revivalCells.add((Cell) cell);
		}
		if (cellState == CellState.DELETE) {
			deleteCells.add((Cell) cell);
		}
	}

	private void enterOfUpdates() {
		for (Cell cell : dyingCells) {
			livingCells.remove(cell);
		}
	}

	public LinkedList<Cell> getLivingCells() {
		return livingCells;
	}

}
