package gameOfLife;

import java.util.LinkedList;

public class GameOfLife {

	private Universe universe;
	private LinkedList<Cell> cells;
	private LinkedList<Cell> livingCells;

	public GameOfLife(LinkedList<Cell> livingCells) {
		cells = new LinkedList<Cell>();
		this.livingCells = livingCells;
		universe = new Universe();
		
		initializeData(livingCells);
		universe.setCells(cells);
	}

	public void initializeData(LinkedList<Cell> livingCells) {
		cells.addAll(livingCells);
		setObserver();
		generateNeighborhoods();
		calculateNumberOfLivingNeighbors();
	}

	private void setObserver() {
		for (Cell cell : cells) {
			cell.setObserver(universe);
		}
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
				Cell neighbor = new Cell(column + i, row + j, CellState.NEIGHBOR, universe);
				if (!cells.contains(neighbor)) {
					cells.add(neighbor);
				}
			}
		}
	}
	
	private void calculateNumberOfLivingNeighbors() {
		for (Cell cell : cells) {
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
		universe.symulationOfCellsLife();
	}

	public LinkedList<Cell> getLivingCells() {
		cells = universe.getCells();
		livingCells.clear();
		for (Cell cell : cells) {
			if (cell.getCellState() == CellState.LIVING) {
				livingCells.add(cell);
			}
		}
		return livingCells;
	}
}
