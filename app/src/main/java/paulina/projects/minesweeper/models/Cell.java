package paulina.projects.minesweeper.models;

import java.util.HashSet;

import static paulina.projects.minesweeper.models.Cell.State.CLOSED;

public class Cell {

    public enum State {
        CLOSED,
        OPEN,
        FLAGGED,
    }

    HashSet<Cell> neighbors;
    boolean isMine;
    boolean isFlagSet;
    State state;

    public Cell() {
        this.state = CLOSED;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isFlagSet() {
        return isFlagSet;
    }

    public void setFlagSet(boolean flagSet) {
        isFlagSet = flagSet;
    }

    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public HashSet<Cell> getNeighbors() {
        return this.neighbors;
    }

    public void setNeighbors(HashSet<Cell> neighbors) {
        this.neighbors = neighbors;
    }
}
