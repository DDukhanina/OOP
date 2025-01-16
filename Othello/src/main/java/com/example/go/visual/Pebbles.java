package com.example.go.visual;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static com.example.go.visual.GameBoard.cellSize;

public class Pebbles extends Circle {//рисуем камушки
    private Color color;

    public Pebbles(double centerX, double centerY, double radius, Color color) {
        super(centerX, centerY, radius);
        this.color = color;
        this.setFill(color);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        this.setFill(color);
    }

    public void placePebbleOnBoard(Group boardGroup) {
        boardGroup.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();

            double centerX = Math.floor(x / cellSize) * cellSize + cellSize / 2;
            double centerY = Math.floor(y / cellSize) * cellSize + cellSize / 2;

            Circle pebble = new Circle(centerX, centerY, 3 * cellSize / 7);
            pebble.setFill(this.getColor());

            boardGroup.getChildren().add(pebble);
        });
    }
}
