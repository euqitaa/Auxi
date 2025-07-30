package com.example.auxi.controller;

import com.example.auxi.Saved.increase;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class pomodoroController implements Initializable {
    Stage stage;
    Parent root;
    Scene scene;


    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progrresslabel;

    private Timeline timeline;
    private int remainingSeconds = 25 * 60;

    private int mode = 3;

    @FXML
    void backbutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void chatbutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/chat.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void homebutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void menu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/user.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void people(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/peopleandranking.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void startButton(ActionEvent event) {
        timeline.play();
    }

    @FXML
    void resetButton(ActionEvent event) {
        timeline.stop();
        if (mode == 3)
            remainingSeconds = 25 * 60;
        else if (mode == 2)
            remainingSeconds = 10 * 60;
        else remainingSeconds = 5 * 60;
        updateTimerLabel();
        progressBar.setProgress(1.0);
    }

    @FXML
    void PauseButton(ActionEvent event) {
        timeline.pause();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progressBar.setProgress(1.0);
        updateTimerLabel();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            remainingSeconds--;
            updateTimerLabel();
            int num = 0;
            if (mode == 1)
                num = 5 * 60;
            else if (mode == 2)
                num = 10 * 60;
            else num = 25 * 60;

            progressBar.setProgress((double) remainingSeconds / num);
            if (remainingSeconds <= 0) {
                try {
                    if (mode == 1) {
                        increase.increasepoints(5);
                    } else if (mode == 2) {
                        increase.increasepoints(10);

                    } else {
                        increase.increasepoints(25);
                    }
                }catch (Exception Ex){
                    Ex.printStackTrace();
                }
                timeline.stop();
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);

    }

    @FXML
    void min10(ActionEvent event) {
        mode = 2;
        timeline.stop();
        remainingSeconds = 10 * 60;
        updateTimerLabel();
        progressBar.setProgress(1.0);
    }

    @FXML
    void min25(ActionEvent event) {
        mode = 3;
        timeline.stop();
        remainingSeconds = 25 * 60;
        updateTimerLabel();
        progressBar.setProgress(1.0);
    }

    @FXML
    void min5(ActionEvent event) {
        mode = 1;
        timeline.stop();
        remainingSeconds = 5 * 60;
        updateTimerLabel();
        progressBar.setProgress(1.0);
    }

    private void updateTimerLabel() {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        progrresslabel.setText(String.format("%02d:%02d", minutes, seconds));
    }
}
