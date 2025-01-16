package com.example.go.visual;

import com.example.go.logic.Board;
import com.example.go.logic.GameLogic;
import com.example.go.logic.PlayerStatus;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class GameBoard {//рисуем доску
    private static int boardSize;
    public static int cellSize;
    private double width;
    private double height;
    private GameLogic gl;
    private Board board;

    public GameBoard(double height, double width) {
        this.height = height;
        this.width = width;

        this.gl = new GameLogic();
        gl.start();
        board = gl.getBoard();

        boardSize = (board.getWidth() + board.getHeight()) / 2;
        cellSize = ((2000) / (2 * boardSize) - (2000) / (20 * boardSize));
    }

    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #CD853F;");
        Scene scene = new Scene(root, width, height);

        Group boardGroup = new Group();
        drawBoard(boardGroup);
        boardGroup.setPickOnBounds(true);

        Color pebbleColor = Color.BLACK;
        if (gl.getPlayer() == PlayerStatus.PLAYER2) {
            pebbleColor = Color.WHITE;
        } else {
            pebbleColor = Color.BLACK;
        }

        Pebbles pebble = new Pebbles(0, 0, cellSize, pebbleColor);
        pebble.placePebbleOnBoard(boardGroup);

        boardGroup.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            //gikb yf[eq 'nb ldt cnhjrb rjlf zyt pyf.? xnj c ybvb cltkfnm
            int x = (int) (event.getX() / cellSize);
            int y = (int) (event.getY() / cellSize);
            System.out.println("Координаты клика: (" + x + ", " + y + ")");
            try {
                gl.makeMove(x, y);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        });

        root.getChildren().add(boardGroup);
        StackPane.setAlignment(boardGroup, Pos.CENTER);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawBoard(Group boardGroup) {
        boardGroup.getChildren().clear();

        for (int i = 0; i < boardSize + 1; i++) {
            for (int j = boardSize; j > -1; j--) {
                Line line1 = new Line(i * cellSize, j, i * cellSize, j * cellSize);
                line1.setStroke(Color.BLACK);
                line1.setStrokeWidth(3);
                boardGroup.getChildren().add(line1);

                Line line2 = new Line(i, j * cellSize, i * cellSize, j * cellSize);
                line2.setStroke(Color.BLACK);
                line2.setStrokeWidth(3);
                boardGroup.getChildren().add(line2);
            }
        }
    }
}