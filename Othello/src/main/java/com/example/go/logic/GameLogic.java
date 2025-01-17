package com.example.go.logic;

public class GameLogic {
    private boolean endGame;
    private static int width = new Board().getWidth();
    private static int height = new Board().getHeight();
    private int pointsPlayer1;
    private int pointsPlayer2;
    private Board board;
    private PlayerStatus player;

    public void start() {
        this.board = new Board();
        initialConditions();
        pointsPlayer1 = 2;
        pointsPlayer2 = 2;
        endGame = false;
        player = PlayerStatus.PLAYER1;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isEndGame() {
        return endGame;
    }

    public int getPointsPlayer1() {
        return pointsPlayer1;
    }

    public int getPointsPlayer2() {
        return pointsPlayer2;
    }

    public void initialConditions() {
        board.setBoardPosition(width / 2 - 1, height / 2 - 1, PlayerStatus.PLAYER2);
        board.setBoardPosition(width / 2 - 1, height / 2, PlayerStatus.PLAYER1);
        board.setBoardPosition(width / 2, height / 2 - 1, PlayerStatus.PLAYER1);
        board.setBoardPosition(width / 2, height / 2, PlayerStatus.PLAYER2);
    }

    public void makeMove(int x, int y) {
        if (board.getPosition(x, y) != 0) {
            throw new IllegalArgumentException("Position already occupied.");
        }

        int[][] allPositions = board.allChecks(x, y, player.getValue());
        if (allPositions.length == 0) {
            throw new IllegalArgumentException("Invalid position");
        }

        board.setBoardPosition(x, y, player);
        flipStones(x, y, allPositions);
        updatePoints();
        isGameOver(x, y);
        reversePlayer();
    }

    private void flipStones(int x, int y, int[][] allPositions) {
        for (int[] position : allPositions) {
            int targetX = position[0];
            int targetY = position[1];
            int dx = Integer.compare(targetX, x);
            int dy = Integer.compare(targetY, y);
            int currX = x + dx;
            int currY = y + dy;
            while (currX != targetX || currY != targetY) {
                board.setBoardPosition(currX, currY, player);
                currX += dx;
                currY += dy;
            }
        }
    }

    private void updatePoints() {
        pointsPlayer1 = 0;
        pointsPlayer2 = 0;

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                if (board.getPosition(i, j) == PlayerStatus.PLAYER1.getValue()) {
                    pointsPlayer1++;
                } else if (board.getPosition(i, j) == PlayerStatus.PLAYER2.getValue()) {
                    pointsPlayer2++;
                }
            }
        }
    }

    public void reversePlayer() {
        player = (player == PlayerStatus.PLAYER1) ? PlayerStatus.PLAYER2 : PlayerStatus.PLAYER1;
    }

    public void isGameOver(int x, int y) {
        if (pointsPlayer1 + pointsPlayer2 == board.getSize() ||
                pointsPlayer1 == 0 || pointsPlayer2 == 0 ||
                board.allChecks(x, y, PlayerStatus.PLAYER1.getValue()).length == 0 &&
                        board.allChecks(x, y, PlayerStatus.PLAYER2.getValue()).length == 0) {
            endGame = true;
        }
    }
}
