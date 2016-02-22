package src.main.java.gameOfLife;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.TreeSet;

public class Cell extends Observable implements Comparable<Cell> {

    private final Integer column;
    private final Integer row;
    private Observer observer;
    private CellState cellState;
    private Integer numberOfLivingNeighbors = 0;
    private final Set<Cell> neighbors;

    // REVIEW dmarkowski - replace Integer with simple ints
    public Cell(Integer column, Integer row, CellState cellState, Observer observer) {
        this.row = row;
        this.column = column;
        this.cellState = cellState;
        this.observer = observer;
        neighbors = new TreeSet<Cell>();
    }

    // REVIEW dmarkowski - unused, please remove
    public Cell(Integer column, Integer row, CellState cellState) {
        this(column, row, cellState, null);
    }

    public Cell(Integer column, Integer row) {
        this(column, row, CellState.REVIVAL, null);
    }

    // REVIEW dmarkowski - the hashCode and equals are not used at all, please remove then
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((column == null) ? 0 : column.hashCode());
        result = prime * result + ((row == null) ? 0 : row.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Cell other = (Cell) obj;
        if (column == null) {
            if (other.column != null) {
                return false;
            }
        }
        else if (!column.equals(other.column)) {
            return false;
        }
        if (row == null) {
            if (other.row != null) {
                return false;
            }
        }
        else if (!row.equals(other.row)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Cell cell) {
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

    // REVIEW dmarkowski - magic numbers 2 and 3, please extract to constants and assign names
    // REVIEW dmarkowski - with those Integers everythings gets so complicated, please change them to ints
    private Boolean changeCellState() {
        if (cellState == CellState.LIVING && !(numberOfLivingNeighbors
                .equals(Integer.valueOf(2)) || numberOfLivingNeighbors.equals(Integer.valueOf(3)))) {
            cellState = CellState.DIES;
        }
        // REVIEW dmarkowski - missing else clause here - potential problem with cell dying and reviving in the same
        // turn
        if (cellState == CellState.NEIGHBOR && numberOfLivingNeighbors.equals(Integer.valueOf(3))) {
            cellState = CellState.REVIVAL;
        }

        // REVIEW dmarkowski - too many ( ) brackets
        // REVIEW dmarkowski - Please use Enumset.of(...).contains() to check agains multiple enums
        return (cellState == CellState.DIES || cellState == CellState.REVIVAL);
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

    // REVIEW dmarkowski - inacurate naming, better would be updateNeighbourCounters or similar
    public void increaseCountersNeighbors() {
        for (Cell neighbor : neighbors) {
            neighbor.increaseCounter();
        }
    }

    private void increaseCounter() {
        numberOfLivingNeighbors++;
    }

    // REVIEW dmarkowski - the same naming problem as with increase
    public void decreaseCountersNeighbors() {
        for (Cell neighbor : neighbors) {
            neighbor.decreaseCounter();
        }
    }

    private void decreaseCounter() {
        numberOfLivingNeighbors--;
    }

    public Integer getColumn() {
        return column;
    }

    public Integer getRow() {
        return row;
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
}
