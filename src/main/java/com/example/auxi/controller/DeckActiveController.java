package com.example.auxi.controller;

import com.example.auxi.Saved.FlashcardNameClass;
import com.example.auxi.Saved.currUser;
import com.example.auxi.Saved.deckNameClass;
import com.example.auxi.db.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeckActiveController implements Initializable {

    Stage stage;
    Parent root;
    Scene scene;

    @FXML
    private Label deck_variable;
    @FXML
    private ListView<VBox> deckList;

    @FXML
    void addFlashcard(ActionEvent event) throws IOException {
                deckNameClass.setDeckname(deck_variable.getText());
                root = FXMLLoader.load(getClass().getResource("/com/example/auxi/addFlashcard.fxml"));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

        }


    @FXML
    void backbutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/flashcard.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        deck_variable.setText(deckNameClass.getDeckname());
        try{
            String c_email = currUser.getEmail();
            Connection con1 = dbConnection.getCOnnection();
            PreparedStatement statement = con1.prepareStatement("SELECT * FROM flashcards WHERE email = ? and deckName = ?");
            statement.setString(1, c_email);
            statement.setString(2, deck_variable.getText());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                VBox vb1 = new VBox();
                vb1.setAlignment(Pos.CENTER);
                Label label1=new Label(resultSet.getString("FrontText"));
                label1.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
                vb1.getChildren().add(label1);
                deckList.getItems().add(vb1);
            }

        }catch (SQLException e){
            System.err.println("Error connecting to database!");
        }

    }
    @FXML
    void handleListViewMouseClicked(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {
            VBox selectedDeck = deckList.getSelectionModel().getSelectedItem();
            Label lb = (Label) selectedDeck.getChildren().get(0);
            String st1 = lb.getText();
            if (!st1.isEmpty()) {
                FlashcardNameClass.setfname(st1);
                root = FXMLLoader.load(getClass().getResource("/com/example/auxi/flashcardactive.fxml"));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }

}
