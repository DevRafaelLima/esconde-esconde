package classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;



public class EscondeEscondeGame {
    private static final int SIZE = 10;
    private static final int MAX_ZORRILHOS = 3;

    private int currentPlayer;
    private int remainingZorrilhosPlayer1;
    private int remainingZorrilhosPlayer2;
    private char[][] board;

    public EscondeEscondeGame() {
        currentPlayer = 2; // Player 2 starts the game
        remainingZorrilhosPlayer1 = MAX_ZORRILHOS;
        remainingZorrilhosPlayer2 = MAX_ZORRILHOS;
        board = new char[SIZE][SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = '.';
            }
        }
    }

     public void playQueroQuero(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || board[row][col] != '.') {
            System.out.println("Posição inválida! Tente novamente.");
            return;
        }

        revealPosition(row, col);
        switchPlayers();
    }

    public void playZorrilho(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE || board[row][col] != '.') {
            System.out.println("Posição inválida! Tente novamente.");
            return;
        }

        if (getCurrentPlayerRemainingZorrilhos() == 0) {
            System.out.println("Você não tem mais Zorrilhos para jogar!");
            return;
        }

        revealPositionAndAdjacent(row, col);
        decreaseCurrentPlayerZorrilhos();
        switchPlayers();
    }

     private void revealPosition(int row, int col) {
        if (board[row][col] == 'a') {
            board[row][col] = '1';
        } else if (board[row][col] == 'b') {
            board[row][col] = '2';
        } else if (board[row][col] == 'c') {
            board[row][col] = '3';
        } else {
            board[row][col] = 'x';
        }
    }

    private void revealPositionAndAdjacent(int row, int col) {
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (isValidPosition(i, j)) {
                    revealPosition(i, j);
                }
            }
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE && board[row][col] != 'x';
    }

    private void switchPlayers() {
        currentPlayer = (currentPlayer == 1) ? 2 : 1;
    }

    private int getCurrentPlayerRemainingZorrilhos() {
        return (currentPlayer == 1) ? remainingZorrilhosPlayer1 : remainingZorrilhosPlayer2;
    }
     private void decreaseCurrentPlayerZorrilhos() {
        if (currentPlayer == 1) {
            remainingZorrilhosPlayer1--;
        } else {
            remainingZorrilhosPlayer2--;
        }
    }

    public void saveGame(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(currentPlayer);
            writer.println(remainingZorrilhosPlayer1);
            writer.println(remainingZorrilhosPlayer2);
            for (char[] row : board) {
                writer.println(row);
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar o jogo: " + e.getMessage());
        }
    }

    public void loadGame(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            currentPlayer = Integer.parseInt(reader.readLine());
            remainingZorrilhosPlayer1 = Integer.parseInt(reader.readLine());
            remainingZorrilhosPlayer2 = Integer.parseInt(reader.readLine());

            for (int i = 0; i < SIZE; i++) {
                String row = reader.readLine();
                for (int j = 0; j < SIZE; j++) {
                    board[i][j] = row.charAt(j);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar o jogo: " + e.getMessage());
        }
    }

    public void printBoard() {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
    public int getCurrentPlayer() {
        return currentPlayer;
    }

}
