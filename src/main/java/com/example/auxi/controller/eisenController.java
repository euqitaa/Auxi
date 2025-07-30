package com.example.auxi.controller;

import com.example.auxi.Saved.currUser;
import com.example.auxi.db.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class eisenController implements Initializable {
    Stage stage;
    Parent root;
    Scene scene;

    @FXML
    private Button TaskAddButton;
    @FXML
    private ListView<String> nurg_imp;

    @FXML
    private ListView<String> nurg_unimp;

    @FXML
    private ListView<String> urg_imp;

    @FXML
    private ListView<String> urg_unimp;

    @FXML
    void addTask(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/AddEisen.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String c_email = currUser.getEmail();
            Connection con1 = dbConnection.getCOnnection();
            PreparedStatement statement = con1.prepareStatement("SELECT * FROM eisenhowerlist WHERE email = ?");
            statement.setString(1, c_email);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int priority=resultSet.getInt("priority");
                String taskTitle =resultSet.getString("TaskTitle");
                String duedate = resultSet.getString("TaskDue");
                if(priority == 1){
                    Label contentLabel = new Label(taskTitle+" - Due: "+duedate);
                    urg_imp.getItems().add(taskTitle+" - Due: "+duedate);
                    //Platform.runLater(() -> urg_imp.getItems().add(String.valueOf(contentLabel)));
                }
                else if(priority == 2){
                    Label contentLabel = new Label(taskTitle+" - Due: "+duedate);
                    nurg_imp.getItems().add(taskTitle+" - Due: "+duedate);
                    //Platform.runLater(() -> nurg_imp.getItems().add(String.valueOf(contentLabel)));

                }
                else if(priority == 3){
                    Label contentLabel = new Label(taskTitle+" - Due: "+duedate);
                    urg_unimp.getItems().add(taskTitle+" - Due: "+duedate);
                    //Platform.runLater(() -> urg_unimp.getItems().add(String.valueOf(contentLabel)));
                } else if(priority == 4){
                    Label contentLabel = new Label(taskTitle+" - Due: "+duedate);
                    nurg_unimp.getItems().add(taskTitle+" - Due: "+duedate);
                    //Platform.runLater(() -> nurg_unimp.getItems().add(String.valueOf(contentLabel)));
                }


            }

            resultSet.close();
            statement.close();
            con1.close();
        } catch (SQLException e) {
            System.err.println("Error connecting to database!");

        }
    }

}
