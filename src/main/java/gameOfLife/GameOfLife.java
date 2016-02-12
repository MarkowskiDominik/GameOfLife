package gameOfLife;

import java.util.LinkedList;

public class GameOfLife {

	private Universe universe;

	public GameOfLife(LinkedList<Cell> livingCells) {
		universe = new Universe(livingCells);
	}

	public void symulationOfCellsLife() {
		universe.symulationOfCellsLife();
	}

	public LinkedList<Cell> getLivingCells() {
		return universe.getLivingCells();
	}
}
