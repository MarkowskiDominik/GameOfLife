package gameOfLife;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GameOfLifeTest {

	private GameOfLife gameOfLife;
	private LinkedList<Cell> livingCells;

	@Before
	public void initializeBowlingGameResultCalculator() {
		livingCells = new LinkedList<Cell>();
	}

	@After
	public void finalizeBowlingGameResultCalculator() {
		gameOfLife = null;
		livingCells = null;
	}

	@Test
	public void oneCell_DeadInTheNextRound() {
		// O O O	O O O
		// O X O => O O O
		// O O O	O O O
		// given
		livingCells.add(new Cell(1, 1, CellState.LIVING));
		
		int expectedSize = 0;
		Cell expectedDeadCell = new Cell(1, 1, CellState.LIVING);

		// when
		gameOfLife = new GameOfLife(livingCells);
		gameOfLife.symulationOfCellsLife();

		// then
		assertEquals(expectedSize, gameOfLife.getLivingCells().size());
		assertFalse(gameOfLife.getLivingCells().contains(expectedDeadCell));
	}
}
