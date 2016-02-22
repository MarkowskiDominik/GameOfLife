package gameOfLife;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.TreeSet;

public class Cell extends Observable implements Comparable<Cell> {

	private static final int THREE_LIVING_NEIGHBORS = 3;
	private static final int TWO_LIVING_NEIGHBORS = 2;
	private final int column;
	private final int row;
	private Observer observer;
	private CellState cellState;
	private int numberOfLivingNeighbors = 0;
	private final Set<Cell> neighbors;

	public Cell(int column, int row, CellState cellState, Observer observer) {
		this.row = row;
		this.column = column;
		this.cellState = cellState;
		this.observer = observer;
		neighbors = new TreeSet<Cell>();
	}

	public Cell(int column, int row) {
		this(column, row, CellState.REVIVAL, null);
	}

    @Override
    public int compareTo(Cell cell) {
    	Integer column = getColumn();
    	Integer row = getRow();
    	
        int result = column.compareTo(cell.getColumn());
        if (result == 0) {
            result = row.compareTo(cell.getRow());
        }
        return result;
    }

	public void setObserver(Observer observer) {
		this.observer = observer;
	}

	public void removeObserver() {
		observer = null;
	}

	@Override
	public void notifyObservers() {
		if (changeCellState()) {
			observer.update(this, cellState);
		}
	}

	private Boolean changeCellState() {
		if (CellState.LIVING.equals(cellState) && !(numberOfLivingNeighbors == TWO_LIVING_NEIGHBORS
				|| numberOfLivingNeighbors == THREE_LIVING_NEIGHBORS)) {
			cellState = CellState.DIES;
		}
		else if (CellState.NEIGHBOR.equals(cellState) && numberOfLivingNeighbors == THREE_LIVING_NEIGHBORS) {
			cellState = CellState.REVIVAL;
		}

		return CellState.DIES.equals(cellState) || CellState.REVIVAL.equals(cellState);
	}

	public void addNeighbor(Cell neighbor) {
		neighbors.add(neighbor);
	}

	public void requestOfRemoveFromNeighbors() {
		for (Cell neighbor : neighbors) {
			neighbor.removeNeighbor(this);
		}
	}

	private void removeNeighbor(Cell neighbor) {
		neighbors.remove(neighbor);
	}

	public void increaseNumberOfLivingNeighborsToCellsOnTheNeighborList() {
		for (Cell neighbor : neighbors) {
			neighbor.increaseCounter();
		}
	}

	private void increaseCounter() {
		numberOfLivingNeighbors++;
	}

	public void decreaseNumberOfLivingNeighborsToCellsOnTheNeighborList() {
		for (Cell neighbor : neighbors) {
			neighbor.decreaseCounter();
		}
	}

	private void decreaseCounter() {
		numberOfLivingNeighbors--;
	}

	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public CellState getCellState() {
		return cellState;
	}

	public void setCellState(CellState cellState) {
		this.cellState = cellState;
	}

	public int getNumberOfLivingNeighbors() {
		return numberOfLivingNeighbors;
	}
}
