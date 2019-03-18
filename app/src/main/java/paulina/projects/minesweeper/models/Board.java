package paulina.projects.minesweeper.models;

import java.util.HashSet;
import java.util.Random;

import static paulina.projects.minesweeper.models.Cell.State.OPEN;

public class Board {

    int size;
    int numMines;
    Cell[][] cells;

    public Board(int size, int numMines) {
        this.size = size;
        this.numMines = numMines;
        cells = new Cell[size][size];

        generateCells();
        storeNeighbors();

        generateMines(numMines);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumMines() {
        return numMines;
    }

    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }

    private void generateMines(int numMines) {
        while (numMines > 0) {
            int randomX = new Random().nextInt(size);
            int randomY = new Random().nextInt(size);
            boolean isMine = cells[randomX][randomY].isMine;
            if (!isMine) {
                cells[randomX][randomY].isMine = true;
                numMines--;
            }
        }
    }

    private void generateCells() {
        for (int rowIndex=0; rowIndex < size; rowIndex++) {
            for (int colIndex=0; colIndex < size; colIndex++) {
                Cell c = new Cell();
                cells[rowIndex][colIndex] = c;
            }
        }
    }

    private void storeNeighbors() {
        for (int rowIndex=0; rowIndex < size; rowIndex++) {
            for (int colIndex=0; colIndex < size; colIndex++) {
                Cell c = cells[rowIndex][colIndex];
                HashSet<Cell> neighbors = new HashSet<>();

                for (int i=-1; i <= 1; i++) {
                    for (int j=-1; j <= 1; j++) {
                        if (isWithinBounds(rowIndex + i, colIndex + j, size)) {
                            if (i != 0 && j != 0) { // cell can't be its own neighbor
                                neighbors.add(cells[rowIndex + i][colIndex + j]);
                            }
                        }
                    }
                }

                c.setNeighbors(neighbors);
            }
        }
    }

    private boolean isWithinBounds(int rowIndex, int colIndex, int size) {
        return (size-1 >= rowIndex && rowIndex >= 0 && size-1 >= colIndex  && colIndex>= 0);
    }

    public void openAllCells() {
        for (int rowIndex=0; rowIndex < size; rowIndex++) {
            for (int colIndex = 0; colIndex < size; colIndex++) {
                cells[colIndex][rowIndex].setState(OPEN);
            }
        }
    }

    public void openNeighbors(Cell c) {
        // base case
        if (c.isMine) {
            return;
        }
        c.setState(OPEN);

        // if not adjacent to mine --> open all neighbors until you hit a mine
        for (Cell neighbor : c.getNeighbors()) {
            openNeighbors(neighbor);
        }
    }
}
