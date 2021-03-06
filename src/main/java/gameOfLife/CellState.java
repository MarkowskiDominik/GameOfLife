package gameOfLife;

public enum CellState {
    LIVING, // living cell
    DIES, // cell living => dead neighbor
    REVIVAL, // cell dead => living
    NEIGHBOR, // dead cell, neighbor of living
}
