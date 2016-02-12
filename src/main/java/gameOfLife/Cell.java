package gameOfLife;

import java.util.Observable;
import java.util.Observer;

public class Cell extends Observable {

	private Integer column;
	private Integer row;
	private Observer observer;
	private CellState cellState;
	private Integer numberOfLivingNeighbors;

	public Cell(Integer column, Integer row, CellState cellState) {
		this.row = row;
		this.column = column;
		this.cellState = cellState;
		if (cellState == CellState.LIVING) {
			numberOfLivingNeighbors = 0;
		}
		numberOfLivingNeighbors = 1;
	}

	public void addObserver(Observer observer) {
		this.observer = observer;
	}

	public void deleteObserver(Observer observer) {
		if (this.observer == observer) {
			observer = null;
		}
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

	public void setNumberOfLivingNeighbors(Integer numberOfLivingNeighbors) {
		this.numberOfLivingNeighbors = numberOfLivingNeighbors;
	}

}
