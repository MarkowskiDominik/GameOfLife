package src.test.java.gameOfLife;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import src.main.java.gameOfLife.Cell;
import src.main.java.gameOfLife.GameOfLife;

public class GameOfLifeTest {

    private GameOfLife gameOfLife;
    private Set<Cell> livingCells;

    @Before
    public void initializeGameOfLife() {
        livingCells = new TreeSet<Cell>();
    }

    @After
    public void finalizeGameOfLife() {
        gameOfLife = null;
        livingCells = null;
    }

    @Test
    public void oneCell_DeadInTheNextRound() {
        // O O O O O O
        // O X O => O O O
        // O O O O O O
        // given
        livingCells.add(new Cell(1, 1));

        int expectedSize = 0;
        Cell expectedDeadCell = new Cell(1, 1);

        // when
        gameOfLife = new GameOfLife(livingCells);
        gameOfLife.symulationOfCellsLife();

        // then
        assertEquals(expectedSize, gameOfLife.getLivingCells().size());
        assertFalse(gameOfLife.getLivingCells().contains(expectedDeadCell));
    }

    @Test
    public void twoCell_DeadInTheNextRound() {
        // O O O O O O O O
        // O X X O => O O O O
        // O O O O O O O O
        // given
        livingCells.add(new Cell(1, 1));
        livingCells.add(new Cell(2, 1));

        int expectedSize = 0;

        // when
        gameOfLife = new GameOfLife(livingCells);
        gameOfLife.symulationOfCellsLife();

        // then
        assertEquals(expectedSize, gameOfLife.getLivingCells().size());
    }

    @Test
    public void threeCell_RevivalOneCellInTheNextRound() {
        // O O O O O O O O
        // O X X O => O X X O
        // O X O O O X X O
        // O O O O O O O O
        // given
        livingCells.add(new Cell(1, 1));
        livingCells.add(new Cell(2, 1));
        livingCells.add(new Cell(1, 2));

        int expectedSize = 4;
        Cell expectedNewCell = new Cell(2, 2);

        // when
        gameOfLife = new GameOfLife(livingCells);
        gameOfLife.symulationOfCellsLife();

        // then
        assertEquals(expectedSize, gameOfLife.getLivingCells().size());
        assertTrue(gameOfLife.getLivingCells().contains(expectedNewCell));
    }

    @Test
    public void fourCellsBlockStruct_NoChangeInTheNextRound() {
        // O O O O O O O O
        // O X X O => O X X O
        // O X X O O X X O
        // O O O O O O O O
        // given
        livingCells.add(new Cell(1, 1));
        livingCells.add(new Cell(1, 2));
        livingCells.add(new Cell(2, 1));
        livingCells.add(new Cell(2, 2));

        // when
        gameOfLife = new GameOfLife(livingCells);
        gameOfLife.symulationOfCellsLife();

        // then
        assertEquals(livingCells.size(), gameOfLife.getLivingCells().size());
        assertTrue(gameOfLife.getLivingCells().containsAll(livingCells) && livingCells
                .containsAll(gameOfLife.getLivingCells()));
    }

    @Test
    public void threeCellsBlinkerStruct_ChangeFromHorizontalToVerticalOrientationInTheNextRound() {
        // O O O O O O O O O O
        // O O O O O O O X O O
        // O X X X O => O O X O O
        // O O O O O O O X O O
        // O O O O O O O O O O
        // given
        livingCells.add(new Cell(1, 2));
        livingCells.add(new Cell(2, 2));
        livingCells.add(new Cell(3, 2));

        TreeSet<Cell> expectedCells = new TreeSet<Cell>();
        expectedCells.add(new Cell(2, 1));
        expectedCells.add(new Cell(2, 2));
        expectedCells.add(new Cell(2, 3));

        // when
        gameOfLife = new GameOfLife(livingCells);
        gameOfLife.symulationOfCellsLife();

        // then
        assertEquals(expectedCells.size(), gameOfLife.getLivingCells().size());
        assertTrue(gameOfLife.getLivingCells().containsAll(expectedCells) && expectedCells
                .containsAll(gameOfLife.getLivingCells()));
    }

    @Test
    public void threeCellsBlinkerStruct_NoChangeInTwoRound() {
        // O O O O O O O O O O
        // O O O O O O O X O O
        // O X X X O => O O X O O
        // O O O O O O O X O O
        // O O O O O O O O O O
        // given
        livingCells.add(new Cell(1, 2));
        livingCells.add(new Cell(2, 2));
        livingCells.add(new Cell(3, 2));

        // when
        gameOfLife = new GameOfLife(livingCells);
        gameOfLife.symulationOfCellsLife();
        gameOfLife.symulationOfCellsLife();

        // then
        assertEquals(livingCells.size(), gameOfLife.getLivingCells().size());
        assertTrue(gameOfLife.getLivingCells().containsAll(livingCells) && livingCells
                .containsAll(gameOfLife.getLivingCells()));
    }

    @Test
    public void threeCellsBlinkerStruct_ChangeFromHorizontalToVerticalOrientationInThreeRound() {
        // O O O O O O O O O O
        // O O O O O O O X O O
        // O X X X O => O O X O O
        // O O O O O O O X O O
        // O O O O O O O O O O
        // given
        livingCells.add(new Cell(1, 2));
        livingCells.add(new Cell(2, 2));
        livingCells.add(new Cell(3, 2));

        TreeSet<Cell> expectedCells = new TreeSet<Cell>();
        expectedCells.add(new Cell(2, 1));
        expectedCells.add(new Cell(2, 2));
        expectedCells.add(new Cell(2, 3));

        // when
        gameOfLife = new GameOfLife(livingCells);
        gameOfLife.symulationOfCellsLife();
        gameOfLife.symulationOfCellsLife();
        gameOfLife.symulationOfCellsLife();

        // then
        assertEquals(expectedCells.size(), gameOfLife.getLivingCells().size());
        assertTrue(gameOfLife.getLivingCells().containsAll(expectedCells) && expectedCells
                .containsAll(gameOfLife.getLivingCells()));
    }

    @Test
    public void oneCell_OneCellDeadFourRevivalInTheNextRound() {
        // O O O O O O O O O O
        // O O X O O O X X X O
        // O X X X O => O X O X O
        // O O X O O O X X X O
        // O O O O O O O O O O
        // given
        livingCells.add(new Cell(2, 1));
        livingCells.add(new Cell(1, 2));
        livingCells.add(new Cell(2, 2));
        livingCells.add(new Cell(3, 2));
        livingCells.add(new Cell(2, 3));

        TreeSet<Cell> expectedCells = new TreeSet<Cell>();
        expectedCells.add(new Cell(1, 1));
        expectedCells.add(new Cell(2, 1));
        expectedCells.add(new Cell(3, 1));
        expectedCells.add(new Cell(1, 2));
        expectedCells.add(new Cell(3, 2));
        expectedCells.add(new Cell(1, 3));
        expectedCells.add(new Cell(2, 3));
        expectedCells.add(new Cell(3, 3));

        // when
        gameOfLife = new GameOfLife(livingCells);
        gameOfLife.symulationOfCellsLife();

        // then
        assertEquals(expectedCells.size(), gameOfLife.getLivingCells().size());
        assertTrue(gameOfLife.getLivingCells().containsAll(expectedCells) && expectedCells
                .containsAll(gameOfLife.getLivingCells()));
    }
}
