package gameOfLife;

import java.util.Observable;
import java.util.Observer;
import java.util.TreeSet;

public class Cell extends Observable implements Comparable<Cell> {

	private Integer column;
	private Integer row;
	private Observer observer;
	private CellState cellState;
	private Integer numberOfLivingNeighbors = 0;
	private TreeSet<Cell> neighbors;
	
	public Cell(Integer column, Integer row, CellState cellState, Observer observer) {
		this.row = row;
		this.column = column;
		this.cellState = cellState;
		this.observer = observer;
		neighbors = new TreeSet<Cell>();
	}

	public Cell(Integer column, Integer row, CellState cellState) {
		this(column, row, cellState, null);
	}

	public Cell(Integer column, Integer row) {
		this(column, row, CellState.REVIVAL, null);
	}

	@Override
	public boolean equals(Object obj) {
		return (this.column.equals(((Cell) obj).getColumn()) && this.row.equals(((Cell) obj).getRow()));
	}

	public int compareTo(Cell cell) {
		int result = column.compareTo(cell.getColumn());
		if (result == 0) {
			result = row.compareTo(cell.getRow());
		}
		return result;
	}

	@Override
	public String toString() {
		return column.toString() + " " + row.toString();
	}

	public void setObserver(Observer observer) {
		this.observer = observer;
	}

	public void removeObserver() {
		observer = null;
	}

	public void notifyObservers() {
		if (changeCellState()) {
			observer.update(this, cellState);
		}
	}
	
	public void addNeighbor(Cell neighbor) {
		neighbors.add(neighbor);
	}

	public void requestOfRemoveToNeighbors() {
		for (Cell neighbor : neighbors) {
			neighbor.removeNeighbor(this);;
		}
	}
	
	public void removeNeighbor(Cell neighbor) {
		neighbors.remove(neighbor);
	}
	
	public void increaseCountersNeighbors() {
		for (Cell neighbor : neighbors) {
			neighbor.increaseCounter();
		}
	}

	public void increaseCounter() {
		numberOfLivingNeighbors++;
	}
	
	public void decreaseCountersNeighbors() {
		for (Cell neighbor : neighbors) {
			neighbor.decreaseCounter();
		}
	}
	
	public void decreaseCounter() {
		numberOfLivingNeighbors--;
	}

	private Boolean changeCellState() {
		if (cellState == CellState.LIVING && !(numberOfLivingNeighbors.equals(Integer.valueOf(2))
				|| numberOfLivingNeighbors.equals(Integer.valueOf(3)))) {
			cellState = CellState.DIES;
		}
		if (cellState == CellState.NEIGHBOR && numberOfLivingNeighbors.equals(Integer.valueOf(3))) {
			cellState = CellState.REVIVAL;
		}
		
		return (cellState != CellState.LIVING && cellState != CellState.NEIGHBOR);
	}

	public Integer getColumn() {
		return column;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public CellState getCellState() {
		return cellState;
	}

	public void setCellState(CellState cellState) {
		this.cellState = cellState;
	}
	
	public Integer getNumberOfLivingNeighbors() {
		return numberOfLivingNeighbors;
	}

	public void setNumberOfLivingNeighbors(Integer numberOfLivingNeighbors) {
		this.numberOfLivingNeighbors = numberOfLivingNeighbors;
	}
}
