package com.example.go.logic;

import java.util.*;

public class Board {
    private final int[][] board;
    private int width;
    private int height;

    public Board() {
        choiceSize();
        width = determineBoardSize(width);
        height = determineBoardSize(height);
        board = new int[width][height];
    }

    private void choiceSize() {
        width = 8;
        height = 8;
    }

    public int getPosition(int i, int j) {
        return board[i][j];
    }

    public int getSize() {
        return width * height;
    }

    public int getWidth() {
        width = determineBoardSize(width);
        return width;
    }

    public int getHeight() {
        height = determineBoardSize(height);
        return height;
    }

    private int determineBoardSize(int size) {
        if (size < 4) {
            throw new IllegalArgumentException("Size should be greater than 4");
        }

        if (size % 2 != 0) {
            return size - 1;
        }

        return size;
    }

    public void setBoardPosition(int x, int y, PlayerStatus player) {
        board[x][y] = player.getValue();
    }

    public int[][] allChecks(int x, int y, int player) {
        Set<int[]> resultSet = new HashSet<>();
        int[][] hor = getHorizontally(x, y, player);
        int[][] ver = getVertically(x, y, player);
        int[][] diag = getDiagonally(x, y, player);

        Collections.addAll(resultSet, hor);
        Collections.addAll(resultSet, ver);
        Collections.addAll(resultSet, diag);

        return resultSet.toArray(new int[0][]);
    }

    public int[][] getHorizontally(int x, int y, int player) {
        List<int[]> indexes = new ArrayList<>();
        PlayerStatus playerNum = PlayerStatus.getByValue(player);

        //влево
        int currentY = y - 1;
        while (currentY >= 0 && board[x][currentY] == reversePlayer(playerNum).getValue()) {
            currentY--;
        }
        if (currentY >= 0 && board[x][currentY] == playerNum.getValue()) {
            for (int i = currentY + 1; i < y; i++) {
                indexes.add(new int[]{x, i});
            }
        }

        //вправо
        currentY = y + 1;
        while (currentY < board[x].length && board[x][currentY] == reversePlayer(playerNum).getValue()) {
            currentY++;
        }
        if (currentY < board[x].length && board[x][currentY] == playerNum.getValue()) {
            for (int i = y + 1; i < currentY; i++) {
                indexes.add(new int[]{x, i});
            }
        }

        return indexes.toArray(new int[0][]);
    }

    public int[][] getVertically(int x, int y, int player) {
        List<int[]> indexes = new ArrayList<>();
        PlayerStatus playerNum = PlayerStatus.getByValue(player);

        //вверх
        int currentX = x - 1;
        while (currentX >= 0 && board[currentX][y] == reversePlayer(playerNum).getValue()) {
            currentX--;
        }
        if (currentX >= 0 && board[currentX][y] == playerNum.getValue()) {
            for (int i = currentX + 1; i < x; i++) {
                indexes.add(new int[]{i, y});
            }
        }

        //вниз
        currentX = x + 1;
        while (currentX < board.length && board[currentX][y] == reversePlayer(playerNum).getValue()) {
            currentX++;
        }
        if (currentX < board.length && board[currentX][y] == playerNum.getValue()) {
            for (int i = x + 1; i < currentX; i++) {
                indexes.add(new int[]{i, y});
            }
        }

        return indexes.toArray(new int[0][]);
    }

    public int[][] getDiagonally(int x, int y, int player) {
        List<int[]> indexes = new ArrayList<>();
        PlayerStatus playerNum = PlayerStatus.getByValue(player);

        int currentX = x;
        int currentY = y;

        //вправо вниз
        while (currentX < board.length && currentY < board[currentX].length && board[currentX][currentY] == reversePlayer(playerNum).getValue()) {
            currentX++;
            currentY++;
        }
        if (currentX < board.length && currentY < board[currentX].length && board[currentX][currentY] == playerNum.getValue()) {
            for (int i = x + 1, j = y + 1; i < currentX && j < currentY; i++, j++) {
                indexes.add(new int[]{i, j});
            }
        }

        //вправо вверх
        currentX = x - 1;
        currentY = y + 1;
        while (currentX >= 0 && currentY < board[currentX].length && board[currentX][currentY] == reversePlayer(playerNum).getValue()) {
            currentX--;
            currentY++;
        }
        if (currentX >= 0 && currentY < board[currentX].length && board[currentX][currentY] == playerNum.getValue()) {
            for (int i = x - 1, j = y + 1; i > currentX && j < currentY; i--, j++) {
                indexes.add(new int[]{i, j});
            }
        }

        //влево вверх
        currentX = x - 1;
        currentY = y - 1;
        while (currentX >= 0 && currentY >= 0 && board[currentX][currentY] == reversePlayer(playerNum).getValue()) {
            currentX--;
            currentY--;
        }
        if (currentX >= 0 && currentY >= 0 && board[currentX][currentY] == playerNum.getValue()) {
            for (int i = x - 1, j = y - 1; i > currentX && j > currentY; i--, j--) {
                indexes.add(new int[]{i, j});
            }
        }

        //влево вниз
        currentX = x + 1;
        currentY = y - 1;
        while (currentX < board.length && currentY >= 0 && board[currentX][currentY] == reversePlayer(playerNum).getValue()) {
            currentX++;
            currentY--;
        }
        if (currentX < board.length && currentY >= 0 && board[currentX][currentY] == playerNum.getValue()) {
            for (int i = x + 1, j = y - 1; i < currentX && j > currentY; i++, j--) {
                indexes.add(new int[]{i, j});
            }
        }

        return indexes.toArray(new int[0][]);
    }

    private PlayerStatus reversePlayer(PlayerStatus player) {
        if (player == PlayerStatus.PLAYER1) {
            return PlayerStatus.PLAYER2;
        } else if (player == PlayerStatus.PLAYER2) {
            return PlayerStatus.PLAYER1;
        }
        return PlayerStatus.FREE;
    }
}
