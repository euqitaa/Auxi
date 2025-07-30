package com.example.auxi.controller;

import com.example.auxi.Saved.currUser;
import com.example.auxi.Saved.increase;
import com.example.auxi.db.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addCalendarController {

    Stage stage;
    Scene scene;
    Parent root;

    @FXML
    private DatePicker datepicker;

    @FXML
    private TextField descriptionTextfield;

    @FXML
    private ImageView imageview;

    @FXML
    private Label messageLabel;

    File selectedFile;


    @FXML
    void backbutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/Calendar.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void chatbutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/chat.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void homebutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/home.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void menu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/user.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void people(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/peopleandranking.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void chooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageview.setImage(image);
        }
    }


    @FXML
    void saveButton(ActionEvent event) {
        String description=descriptionTextfield.getText();
        String email= currUser.getEmail();

        if (description != null && datepicker.getValue() != null && selectedFile != null) {
            try {
                Connection connection = dbConnection.getCOnnection();
                String sql = "INSERT INTO calendar (email, description, image, date) VALUES (?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);


                statement.setString(1, email);
                statement.setString(2, description);
                statement.setDate(4, java.sql.Date.valueOf(datepicker.getValue()));


                InputStream inputStream = new FileInputStream(selectedFile);
                statement.setBlob(3, inputStream);

                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    increase.increasepoints(3);
                    root = FXMLLoader.load(getClass().getResource("/com/example/auxi/Calendar.fxml"));
                    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    messageLabel.setText("Failed to save event!");
                }
                statement.close();
                connection.close();
            } catch (SQLException | FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            messageLabel.setText("Please fill in all fields!");
        }
    }


}
