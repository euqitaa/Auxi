package com.example.auxi.controller;

import com.example.auxi.Saved.currUser;
import com.example.auxi.Saved.deckNameClass;
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

public class AddFlashcardController {

    Stage stage;
    Parent root;
    Scene scene;

    @FXML
    private TextField backText;

    @FXML
    private TextField frontText;


    @FXML
    void addFlashCard(ActionEvent event) {
        String c_email = currUser.getEmail();
        String ftext = frontText.getText();
        String btext = backText.getText();
        String deckname = deckNameClass.getDeckname();

        if(!ftext.isEmpty() && !btext.isEmpty()){
            try {
                Connection con = dbConnection.getCOnnection();
                PreparedStatement statement = con.prepareStatement("INSERT INTO flashcards (email,FrontText,BackText,DeckName) VALUES (?, ?,?,?)");
                statement.setString(1, c_email);
                statement.setString(2, ftext);
                statement.setString(3, btext);
                statement.setString(4, deckname);
                statement.executeUpdate();

                root = FXMLLoader.load(getClass().getResource("/com/example/auxi/deckActive.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    @FXML
    void backbutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/flashcard.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
