package com.example.auxi.controller;

import com.example.auxi.Saved.FlashcardNameClass;
import com.example.auxi.Saved.currUser;
import com.example.auxi.Saved.deckNameClass;
import com.example.auxi.db.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FlashcardactiveController implements Initializable {
    Stage stage;
    Parent root;
    Scene scene;


    @FXML
    private Label flashcardname;

    @FXML
    private Label frontLabel;

    @FXML
    private Label lname;

    String answersave;

    @FXML
    void backbutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/deckactive.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void showAnswer(ActionEvent event) {
        lname.setText(answersave);
        lname.setStyle(" -fx-font-size: 18; -fx-border-color: black; -fx-background-color: white;");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flashcardname.setText(FlashcardNameClass.getfname());
        try{
            String c_email = currUser.getEmail();
            Connection con1 = dbConnection.getCOnnection();
            PreparedStatement statement = con1.prepareStatement("SELECT * FROM flashcards WHERE email = ? and deckName = ? and FrontText = ?");
            statement.setString(1, c_email);
            statement.setString(2, deckNameClass.getDeckname());
            statement.setString(3, flashcardname.getText());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                frontLabel.setText(resultSet.getString("FrontText"));
                frontLabel.setStyle(" -fx-font-size: 18; -fx-border-color: black; -fx-background-color: white;");
                answersave = resultSet.getString("BackText");
            }

        }catch (SQLException e){
            System.err.println("Error connecting to database!");
        }
    }
}
