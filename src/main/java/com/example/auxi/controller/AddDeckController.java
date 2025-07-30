package com.example.auxi.controller;

import com.example.auxi.Saved.currUser;
import com.example.auxi.db.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddDeckController {

    Stage stage;
    Parent root;
    Scene scene;


    @FXML
    private TextField deckName;

    @FXML
    void backbutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/flashcard.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void confirmDeck(ActionEvent event) {
        String deck = deckName.getText();
        String c_email = currUser.getEmail();

        if (!deck.isEmpty() && !c_email.isEmpty()) {
            try {
                Connection con = dbConnection.getCOnnection();
                PreparedStatement statement = con.prepareStatement("INSERT INTO flashcardsdeck (email,DeckName) VALUES (?,?)");
                statement.setString(1, c_email);
                statement.setString(2, deck);
                statement.executeUpdate();

                root = FXMLLoader.load(getClass().getResource("/com/example/auxi/flashcard.fxml"));
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
