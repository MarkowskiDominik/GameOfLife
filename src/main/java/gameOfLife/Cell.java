package gameOfLife;

import java.util.Observable;
import java.util.Observer;

public class Cell extends Observable {

	private Integer column;
	private Integer row;
	private Observer observer;
	private CellState cellState;
	private Integer numberOfLivingNeighbors = 0;
	
	public Cell(Integer column, Integer row, CellState cellState, Observer observer) {
		this.row = row;
		this.column = column;
		this.cellState = cellState;
		this.observer = observer;
	}

	public Cell(Integer column, Integer row, CellState cellState) {
		this(column, row, cellState, null);
	}

	public Cell(Integer column, Integer row) {
		this(column, row, CellState.LIVING, null);
	}

	@Override
	public boolean equals(Object obj) {
		return (this.column.equals(((Cell) obj).getColumn()) && this.row.equals(((Cell) obj).getRow()));
	}

	@Override
	public String toString() {
		return column.toString() + " " + row.toString();
	}

	public void setObserver(Observer observer) {
		this.observer = observer;
	}

	public void deleteObserver() {
		observer = null;
	}

	public void notifyObservers() {
		if (changeCellState()) {
			observer.update(this, cellState);
		}
	}

	private Boolean changeCellState() {
		if (cellState == CellState.LIVING && !(numberOfLivingNeighbors.equals(Integer.valueOf(2))
				|| numberOfLivingNeighbors.equals(Integer.valueOf(3)))) {
			cellState = CellState.DYING;
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
