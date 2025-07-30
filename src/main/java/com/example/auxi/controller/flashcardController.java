package com.example.auxi.controller;

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

public class flashcardController implements Initializable {
    Stage stage;
    Parent root;
    Scene scene;

    @FXML
    private ListView<VBox> deckList;

    private VBox selectedvbox;

    @FXML
    void addDeck(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/addDeck.fxml"));
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

        try{
            String c_email = currUser.getEmail();
            Connection con1 = dbConnection.getCOnnection();
            PreparedStatement statement = con1.prepareStatement("SELECT * FROM flashcardsdeck WHERE email = ?");
            statement.setString(1, c_email);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                VBox vb1 = new VBox();
                vb1.setAlignment(Pos.CENTER);
                Label label1=new Label(resultSet.getString("deckName"));
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
                deckNameClass.setDeckname(st1);
                root = FXMLLoader.load(getClass().getResource("/com/example/auxi/deckActive.fxml"));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        }
    }


    @FXML
    void delete(ActionEvent event) throws IOException {
        int selectedIndex = deckList.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            VBox selectedVBox = deckList.getItems().remove(selectedIndex);
            deleteflash(selectedVBox);
            deleteNoteFromDatabase(selectedVBox);
            root = FXMLLoader.load(getClass().getResource("/com/example/auxi/flashcard.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    private void deleteNoteFromDatabase(VBox vBox) {
        try {
            Connection con = dbConnection.getCOnnection();
            String sql = "DELETE FROM flashcardsdeck WHERE deckName = ? and email = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, ((Label) vBox.getChildren().get(0)).getText());
            statement.setString(2, currUser.getEmail());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Note deleted from the database.");
            } else {
                System.out.println("Failed to delete note from the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteflash(VBox vBox) {
        try {
            Connection con = dbConnection.getCOnnection();
            String sql = "DELETE FROM flashcards WHERE deckName = ? and email = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, ((Label) vBox.getChildren().get(0)).getText());
            statement.setString(2, currUser.getEmail());
            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Note deleted from the database.");
            } else {
                System.out.println("Failed to delete note from the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
