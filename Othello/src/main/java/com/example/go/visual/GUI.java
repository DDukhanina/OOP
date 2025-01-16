package com.example.go.visual;

import com.example.go.logic.GameLogic;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GUI extends Application {//рисуем интерфейс
    private ArrayList<Pebbles> pebbles;
    private GameLogic gl = new GameLogic();
    private double height;
    private double width;

    @Override
    public void start(Stage primaryStage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        width = screenBounds.getWidth();
        height = screenBounds.getHeight();

        primaryStage.setWidth(width);
        primaryStage.setHeight(height);

        Label label = new Label("Othello");
        label.setStyle("-fx-font-size: 90px; -fx-font-weight: bold;");

        Button rulesButton = new Button("Правила");
        rulesButton.setStyle("-fx-font-size: 50px;");
        rulesButton.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Правила игры Othello");
            alert.setHeaderText(null);

            TextFlow textFlow = new TextFlow();
            textFlow.setPrefWidth(600);

            Text text = new Text(" \n" + "1. Перед игрой в центр игрового поля ставятся четыре фишки (2 белые и 2 черные). Первый ход делает игрок черных фишек.\n" + " \n" +
                    "2. Фишку необходимо положить на одну из клеток поля таким образом, чтобы между этой фишкой и другой фишкой этого же цвета, " +
                    "находился непрерывный ряд фишек оппонента (вертикально, диагонально или горизонтально). При этом все фишки оппонента, которые игрок " +
                    "закрыл, переходят к ходившему игроку.\n" + " \n" +
                    "3. Отказываться от хода запрещается. Если нет возможности осуществить ход, то ход переходит оппоненту.\n" + " \n" +
                    "4. Игра завершается, когда ни один игрок не может сделать ход.\n" + " \n" +
                    "5. По завершению игры осуществляется подсчет белых и черных фишек. У кого фишек на игровом поле больше, тот и победил. " +
                    "Если на игровом поле черны и белых фишек одинаковое количество, то объявляется ничья."
            );
            text.setStyle("-fx-font-size: 16pt");

            textFlow.getChildren().add(text);
            alert.getDialogPane().setContent(textFlow);
            alert.showAndWait();
        });

        Button gameButton = new Button("Играть");
        gameButton.setStyle("-fx-font-size: 60px;");
        gameButton.setOnAction(e -> {
            GameBoard gameBoard = new GameBoard(height, width);
            gameBoard.start(primaryStage);
        });

        if (gl.isEndGame()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Конец игры");
            alert.setHeaderText(null);
            alert.setContentText("Выиграли чёрные!");

            ButtonType playAgainButton = new ButtonType("Сыграть ещё");
            ButtonType exitButton = new ButtonType("Выйти");

            alert.getButtonTypes().setAll(playAgainButton, exitButton);
            alert.showAndWait().ifPresent(response -> {
                if (response == playAgainButton) {
                    // Логика для начала новой игры
                } else if (response == exitButton) {
                    // Логика для выхода из игры
                    System.exit(0);
                }
            });
        };

        VBox layout = new VBox(30);
        layout.setStyle("-fx-background-color: #CD853F;");
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, rulesButton, gameButton);

        Scene scene = new Scene(layout, width, height);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Othello offline");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}