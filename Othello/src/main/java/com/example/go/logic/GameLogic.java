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

    public void initialConditions() {
        board.setBoardPosition(width / 2 - 1, height / 2 - 1, PlayerStatus.PLAYER2);
        board.setBoardPosition(width / 2 - 1, height / 2, PlayerStatus.PLAYER1);
        board.setBoardPosition(width / 2, height / 2 - 1, PlayerStatus.PLAYER1);
        board.setBoardPosition(width / 2, height / 2, PlayerStatus.PLAYER2);
    }

    public void makeMove(int x, int y) {
        int[][] allPosition = board.allChecks(x, y, player.getValue());

        if (allPosition.length != 0 && board.getPosition(x, y) == 0) {
            board.setBoardPosition(x, y, player);

            for (int[] position : allPosition) {
                int targetX = position[0];
                int targetY = position[1];

                if (x < targetX && y < targetY) {
                    for (int i = x; i <= targetX; i++) {
                        for (int j = y; j <= targetY; j++) {
                            board.setBoardPosition(i, j, player);
                        }
                    }
                } else if (x < targetX && y > targetY) {
                    for (int i = x; i <= targetX; i++) {
                        for (int j = y; j >= targetY; j--) {
                            board.setBoardPosition(i, j, player);
                        }
                    }
                } else if (x > targetX && y < targetY) {
                    for (int i = x; i >= targetX; i--) {
                        for (int j = y; j <= targetY; j++) {
                            board.setBoardPosition(i, j, player);
                        }
                    }
                } else {
                    for (int i = x; i >= targetX; i--) {
                        for (int j = y; j >= targetY; j--) {
                            board.setBoardPosition(i, j, player);
                        }
                    }
                }
            }
            isGameOver(x, y);
            reversePlayer();
        } else {
            throw new IllegalArgumentException("Invalid position");
        }
    }

    public PlayerStatus getPlayer() {
        return player;
    }

    public void reversePlayer() {
        if (player == PlayerStatus.PLAYER2) {
            player = PlayerStatus.PLAYER1;
        } else if (player == PlayerStatus.PLAYER1) {
            player = PlayerStatus.PLAYER2;
        }
    }

    public void isGameOver(int x, int y) {
        if (pointsPlayer2 + pointsPlayer1 == board.getSize() ||
                pointsPlayer1 == 0 || pointsPlayer2 == 0 ||
                board.allChecks(x, y, PlayerStatus.PLAYER1.getValue()).length == 0 &&
                        board.allChecks(x, y, PlayerStatus.PLAYER2.getValue()).length == 0) {
            checkWinner();
            endGame = true;
        }
    }

    public String checkWinner() {
        if (pointsPlayer1 > pointsPlayer2) {
            return "Win player 1";
        } else if (pointsPlayer1 < pointsPlayer2) {
            return "Win player 2";
        }
        return "Drawn game";
    }
}