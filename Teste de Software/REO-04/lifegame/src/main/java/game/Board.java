package game;

import java.util.Arrays;
import java.util.Random;

public class Board {
    private int[][] board;
    private final int DIMENSION;

    public Board(int dimension) {
        DIMENSION = dimension;
        board = new int[dimension][dimension];
    }

    public Board(Board otherBoard) {
        DIMENSION = otherBoard.DIMENSION;
        setBoard(otherBoard);
    }

    public void setBoard(Board otherBoard) {
        if (DIMENSION == otherBoard.DIMENSION) {
            board = otherBoard.getCopy();
        }
    }

    public void setCellState(Coordinate cellCoordinate, int value) {
        if (cellIsValid(cellCoordinate)) {
            board[cellCoordinate.X][cellCoordinate.Y] = value;
        }
    }

    public int getCellState(Coordinate cellCoordinate) {
        return board[cellCoordinate.X][cellCoordinate.Y];
    }

    public void initializeBoard() {
        for (int i = 0; i < DIMENSION; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                Random randomNumber = new Random();
                int value = randomNumber.nextInt(2);
                board[i][j] = value;
            }
        }
    }

    public int[][] getCopy() {
        int[][] copy = new int[DIMENSION][DIMENSION];

        for (int i = 0; i < DIMENSION; i++) {
            copy[i] = Arrays.copyOf(board[i], board[i].length);
        }

        return copy;
    }

    public boolean cellIsValid(Coordinate cellCoordinate) {
        return cellCoordinate.X >= 0 && cellCoordinate.X < DIMENSION
                && cellCoordinate.Y >= 0 && cellCoordinate.Y < DIMENSION;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int[] line : board) {
            str.append("|");
            for (int field : line) {
                str.append(field).append("|");
            }
            str.append("\n");
        }

        return str.toString();
    }
}
