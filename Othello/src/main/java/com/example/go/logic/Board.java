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

    public int getLength(){
        return board.length;
    }

    public int getLength0(){
        return board[0].length;
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
        resultSet.add(new int[]{x, y});

        Collections.addAll(resultSet, hor);
        Collections.addAll(resultSet, ver);
        Collections.addAll(resultSet, diag);

        return resultSet.toArray(new int[0][]);
    }

    public int[][] getHorizontally(int x, int y, int player) {
        List<int[]> indexes = new ArrayList<>();
        PlayerStatus playerNum = PlayerStatus.getByValue(player);

        //влево
        int currentY = y;
        while (currentY >= 0 && board[x][currentY] == reversePlayer(playerNum).getValue()) {
            currentY--;
        }

        //вправо
        currentY = y + 1;
        while (currentY < board[x].length && board[x][currentY] == reversePlayer(playerNum).getValue()) {
            indexes.add(new int[]{x, currentY});
            currentY++;
        }

        return indexes.toArray(new int[0][]);
    }

    public int[][] getVertically(int x, int y, int player) {
        List<int[]> indexes = new ArrayList<>();
        PlayerStatus playerNum = PlayerStatus.getByValue(player);

        //вверх
        int currentX = x;
        while (currentX >= 0 && board[currentX][y] == reversePlayer(playerNum).getValue()) {
            currentX--;
        }

        //вниз
        currentX = x + 1;
        while (currentX < board.length && board[currentX][y] == reversePlayer(playerNum).getValue()) {
            indexes.add(new int[]{currentX, y});
            currentX++;
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
            indexes.add(new int[]{currentX, currentY});
            currentX++;
            currentY++;
        }

        //вправо вверх
        while (currentX >= 0 && currentY < board[currentX].length && board[currentX][currentY] == reversePlayer(playerNum).getValue()) {
            indexes.add(new int[]{currentX, currentY});
            currentX--;
            currentY++;
        }

        //влево вверх
        while (currentX >= 0 && currentY >= 0 && board[currentX][currentY] == reversePlayer(playerNum).getValue()) {
            indexes.add(new int[]{currentX, currentY});
            currentX--;
            currentY--;
        }

        //влево вниз
        while (currentX < board.length && currentY >= 0 && board[currentX][currentY] == reversePlayer(playerNum).getValue()) {
            indexes.add(new int[]{currentX, currentY});
            currentX++;
            currentY--;
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