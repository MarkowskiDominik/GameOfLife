package gameOfLife;

import java.util.TreeSet;

public class GameOfLife {

	private Universe universe;
	private TreeSet<Cell> cells;

	public GameOfLife(TreeSet<Cell> cells) {
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
