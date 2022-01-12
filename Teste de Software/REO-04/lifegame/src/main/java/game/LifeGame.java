package game;

import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

public class LifeGame {
    private enum NeighborPosition {
        LEFT(-1, 0),
        RIGHT(1, 0),
        TOP(0, -1),
        TOP_LEFT(-1, -1),
        TOP_RIGHT(1, -1),
        BOTTOM(0, 1),
        BOTTOM_LEFT(-1, 1),
        BOTTOM_RIGHT(1, 1);

        public int horizontalFactor;
        public int verticalFactor;
        NeighborPosition (int horizontalFactor, int verticalFactor) {
            this.horizontalFactor = horizontalFactor;
            this.verticalFactor = verticalFactor;
        }
    }

    private final int BOARD_DIMENSION = 6;
    private final int ALIVE_STATE = 1;
    private final int DEAD_STATE = 0;
    private final Board oldBoard;
    private final Board board;
    private boolean firstGeneration = true;

    public LifeGame() {
        board = new Board(BOARD_DIMENSION);
        oldBoard = new Board(BOARD_DIMENSION);
    }

    public void run() {
        boolean mustContinue = true;

         do {
             getNewGeneration();

             System.out.println("Tabuleiro anterior");
             System.out.println(oldBoard.toString());

             System.out.println("Tabuleiro atual");
             System.out.println(board.toString());
             mustContinue = askIfItShouldContinue();
        } while (mustContinue);
    }

    private boolean askIfItShouldContinue() {
        Scanner sc = new Scanner(System.in);
        String response = "";

        do {
            System.out.print("Deseja continuar? (S/N) ");
            response = sc.nextLine().trim().toLowerCase();
        } while (!response.equals("s") && !response.equals("n"));

        System.out.println("");
        return response.equals("s");
    }

    private void getNewGeneration() {
        if (firstGeneration) {
            board.initializeBoard();
            oldBoard.setBoard(board);
            firstGeneration = false;
        } else {
            Board auxBoard = new Board(board);
            oldBoard.setBoard(board);

            for (int i = 0; i < BOARD_DIMENSION; i++) {
                for (int j = 0; j < BOARD_DIMENSION; j++) {
                    Coordinate cellCoordinate = new Coordinate(i, j);
                    int currentState = board.getCellState(cellCoordinate);
                    int quantityOfAliveNeighbors = getQuantityOfAliveNeighbors(cellCoordinate);

                    int nextState = getNextState(currentState, quantityOfAliveNeighbors);

                    if (nextState == -1) {
                        throw new IllegalStateException("Valor do estado ou número de vizinhos inválido!");
                    }

                    auxBoard.setCellState(cellCoordinate, nextState);
                }
            }

            board.setBoard(auxBoard);
        }
    }

    private int getNextState (int currentState, int quantityOfAliveNeighbors) {
        if (quantityOfAliveNeighbors < 0 || quantityOfAliveNeighbors > 8
            || !(currentState == ALIVE_STATE || currentState == DEAD_STATE)) {
            return -1;
        }

        if (currentState == ALIVE_STATE) {
            if (quantityOfAliveNeighbors < 2 || quantityOfAliveNeighbors > 3) return DEAD_STATE;
        }

        if (currentState == DEAD_STATE) {
            if (quantityOfAliveNeighbors == 3) return ALIVE_STATE;
        }

        return currentState;
    }

    private int getQuantityOfAliveNeighbors(Coordinate cellCoordinate) {
        Stream<NeighborPosition> nbPositionStream = Stream.of(NeighborPosition.values());

        return nbPositionStream.reduce(0, (totalAlive, nbPosition) -> {
            Optional<Integer> neighborState = getNeighborState(cellCoordinate, nbPosition);

            if (neighborState.isPresent()) {
                totalAlive = totalAlive + neighborState.get();
            }

            return totalAlive;
        }, Integer::sum);
    }

    private Optional<Integer> getNeighborState(Coordinate cellCoordinate, NeighborPosition nbPosition) {
        int nbCoordX = cellCoordinate.X + nbPosition.horizontalFactor;
        int nbCoordY = cellCoordinate.Y + nbPosition.verticalFactor;
        Coordinate nbCoordinate = new Coordinate(nbCoordX, nbCoordY);

        boolean isValid = board.cellIsValid(nbCoordinate);

        if (board.cellIsValid(nbCoordinate)) return Optional.of(board.getCellState(nbCoordinate));

        return Optional.empty();
    }
}
