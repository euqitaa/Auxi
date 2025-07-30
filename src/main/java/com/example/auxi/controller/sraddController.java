package com.example.auxi.controller;

import com.example.auxi.Saved.currUser;
import com.example.auxi.db.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;

public class sraddController {

    @FXML
    private TextField topicInput;
    @FXML
    private MenuButton DayGroup;

    Stage stage;
    Parent root;
    Scene scene;


    @FXML
    void handleDayGroupSelection(ActionEvent event) {
        MenuItem selectedMenuItem = (MenuItem) event.getSource();
        String dayGroupValue = selectedMenuItem.getUserData().toString();
        DayGroup.setText(dayGroupValue);
    }

    @FXML
    void backbutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/home.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void saveTopic(ActionEvent event) {
        String topicName = topicInput.getText();
        int dayGroupVal = Integer.parseInt(DayGroup.getText());
        String email = currUser.getEmail();
        LocalDate currentDate = LocalDate.now();
        if(!topicName.isEmpty() && (dayGroupVal==1 || dayGroupVal==2)){
            try {
                Connection con = dbConnection.getCOnnection();
                PreparedStatement statement = con.prepareStatement("INSERT INTO spacedrep (email,TopicName,DayGroup,LastUpdateDate) VALUES (?, ?,?,?)");
                statement.setString(1, email);
                statement.setString(2, topicName);
                statement.setInt(3, dayGroupVal);
                statement.setDate(4, java.sql.Date.valueOf(currentDate));

                statement.executeUpdate();

                root = FXMLLoader.load(getClass().getResource("/com/example/auxi/SR.fxml"));
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
