package com.example.gane;


import java.util.Objects;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PingPong extends Application {
    private static final int width = 800;
    private static final int height = 600;
    private static final int player_height = 100;
    private static final int player_width = 15;
    private static final int bal_R = 15;
    private int ballYspeed = 1;
    private int ballXspeed = 1;
    private double playerOneYPos = height / 2;
    private double playerTwoYPos = height / 2;
    private double ballXPos = width / 2;
    private double ballYPos = width / 2;
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    private boolean gameStarted;
    private final int playerOneXPos = 0;
    private int playerTwoXPos = width - player_width;

    public void start(Stage stage) throws Exception {
        stage.setTitle("PingPong");
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/pong.jpg")).toString()));

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        tl.setCycleCount(Timeline.INDEFINITE);

        canvas.setOnMouseMoved(e -> playerOneYPos = e.getY());
        canvas.setOnMouseClicked(e -> gameStarted = true);
        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
        tl.play();
    }



    private void run(GraphicsContext gc) {
        int once = 0;

        gc.setFill(Color.YELLOWGREEN);
        gc.fillRect(0, 0, width, height);


        gc.setFill(Color.AQUAMARINE);
        gc.setFont(Font.font(25));

        if (gameStarted) {

            ballXPos += ballXspeed;
            ballYPos += ballYspeed;

            playerTwoYPos += (ballYPos > playerTwoYPos + player_height / 2.0) ? 3 : - 3;
            gc.fillOval(ballXPos, ballYPos, bal_R, bal_R);
        } else {
             gc.setStroke(Color.BLACK);
             gc.setTextAlign(TextAlignment.CENTER);gc.strokeText("Play", width / 2, height / 2);

            ballXPos = width / 2;
            ballYPos = height / 2;

            ballXspeed = new Random().nextInt(2) == 0 ? 1 : -1;
            ballYspeed = new Random().nextInt(2) == 0 ? 1 : -1;
        }

        if (ballYPos > height || ballYPos < 0) ballYspeed *= -1;

        if(gameStarted) {
            if ((ballXPos < playerOneXPos - player_width)){
                scoreP2++;
                gameStarted = false;
            }else if( ballXPos > playerTwoXPos - player_width + 5){
                scoreP1++;
                gameStarted = false;
            }
        }

        if (((ballXPos + bal_R > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + player_height) ||
                ((ballXPos < playerOneXPos + player_width) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + player_height)) {
            ballYspeed += 1 * Math.signum(ballYspeed);
            ballXspeed += 1 * Math.signum(ballXspeed);
            ballXspeed *= -1;
            ballYspeed *= 1;
        }

        gc.fillText(scoreP1 + "\t\t\t\t\t\t\t\t" + scoreP2, width / 2, 100);


        gc.fillRect(playerTwoXPos, playerTwoYPos, player_width, player_height);
        gc.fillRect(playerOneXPos, playerOneYPos, player_width, player_height);
    }

    public static void main(String[] args) {
        launch(args);
    }

}