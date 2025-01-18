package com.example.go.visual;

import com.example.go.logic.Board;
import com.example.go.logic.GameLogic;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class GameBoard {

    private static int boardSize;
    public static int cellSize;
    private double width;
    private double height;
    private GameLogic gl;
    private Board board;
    private static final int margin = 50;

    public GameBoard(double height, double width) {
        this.height = height;
        this.width = width;
        this.gl = new GameLogic();
        gl.start();
        board = gl.getBoard();
        boardSize = board.getWidth();
        double availableWidth = width - 2 * margin;
        double availableHeight = height - 2 * margin;
        cellSize = (int) Math.min(availableWidth, availableHeight) / boardSize;
    }

    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #CD853F;");
        root.setPadding(new javafx.geometry.Insets(margin));

        Scene scene = new Scene(root, width, height);
        Group boardGroup = new Group();
        drawBoard(boardGroup);
        boardGroup.setPickOnBounds(true);

        boardGroup.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            int x = (int) Math.floor((event.getX() - margin) / cellSize);
            int y = (int) Math.floor((event.getY() - margin) / cellSize);

            if (x >= 0 && x < boardSize && y >= 0 && y < boardSize) {
                try {
                    gl.makeMove(x, y);
                    updateBoardView(boardGroup);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Try again.");
            }
        });

        updateBoardView(boardGroup);
        root.getChildren().add(boardGroup);
        StackPane.setAlignment(boardGroup, Pos.CENTER);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateBoardView(Group boardGroup) {
        boardGroup.getChildren().clear();
        drawBoard(boardGroup);

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                Color color;
                switch (board.getPosition(i, j)) {
                    case 1:
                        color = Color.BLACK;
                        break;
                    case 2:
                        color = Color.WHITE;
                        break;
                    default:
                        continue;
                }

                Pebbles cell = new Pebbles(i, j, cellSize, color);
                cell.placePebbleOnBoard(boardGroup);
            }
        }
    }

    private void drawBoard(Group boardGroup) {
        for (int i = 0; i <= boardSize; i++) {
            Line line = new Line(margin + i * cellSize, margin, margin + i * cellSize, margin + boardSize * cellSize);
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(2);
            boardGroup.getChildren().add(line);
        }

        for (int j = 0; j <= boardSize; j++) {
            Line line = new Line(margin, margin + j * cellSize, margin + boardSize * cellSize, margin + j * cellSize);
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(2);
            boardGroup.getChildren().add(line);
        }
    }
}
