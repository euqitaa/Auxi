package com.example.auxi.controller;

import com.example.auxi.Saved.currUser;
import com.example.auxi.db.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddEisenController {
    Stage stage;
    Parent root;
    Scene scene;

    @FXML
    private TextField taskname;

    @FXML
    private DatePicker date;

    @FXML
    private MenuButton prioritymenu;

    @FXML
    private Button saveTaskButton;

    @FXML
    void backbutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/eisen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void handlePrioritySelection(ActionEvent event) {
        MenuItem selectedMenuItem = (MenuItem) event.getSource();
        String priorityValue = selectedMenuItem.getUserData().toString();
        prioritymenu.setText(priorityValue);
    }

    @FXML
    void saveTask(ActionEvent event) throws IOException {
        String taskName = taskname.getText();
        String taskDate = date.getValue().toString();
        int taskPriority = Integer.parseInt(prioritymenu.getText());

        String email = currUser.getEmail();

        if (!taskName.isEmpty() && !taskDate.isEmpty()) {
            try {
                Connection con = dbConnection.getCOnnection();
                PreparedStatement statement = con.prepareStatement("INSERT INTO eisenhowerlist (email,TaskTitle,priority,TaskDue) VALUES (?, ?,?,?)");
                statement.setString(1, email);
                statement.setString(2, taskName);
                statement.setInt(3, taskPriority);
                statement.setString(4, taskDate);
                statement.executeUpdate();

                root = FXMLLoader.load(getClass().getResource("/com/example/auxi/eisen.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }
}
