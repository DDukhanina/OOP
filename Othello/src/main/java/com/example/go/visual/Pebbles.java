package com.example.go.visual;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pebbles {
    private int x;
    private int y;
    private int cellSize;
    private Color color;

    public Pebbles(int x, int y, int cellSize, Color color) {
        this.x = x;
        this.y = y;
        this.cellSize = cellSize;
        this.color = color;
    }

    public void placePebbleOnBoard(Group boardGroup) {
        Circle pebble = new Circle((x + 0.93) * cellSize, (y + 0.93) * cellSize, 5*cellSize / 13);
        pebble.setFill(color);
        boardGroup.getChildren().add(pebble);
    }
}
