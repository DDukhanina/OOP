package com.example.go.logic;

public enum PlayerStatus {
    FREE(0),
    PLAYER1(1),
    PLAYER2(2);

    private final int value;

    PlayerStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static PlayerStatus getByValue(int value) {
        for (PlayerStatus playerStatus: PlayerStatus.values()) {
            if (playerStatus.value == value) {
                return playerStatus;
            }
        }
        throw new IllegalArgumentException("No player with value " + value);
    }
}
