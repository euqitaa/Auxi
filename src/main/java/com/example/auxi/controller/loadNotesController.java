package com.example.auxi.controller;

import com.example.auxi.Saved.people;
import com.example.auxi.db.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class loadNotesController implements Initializable {

    Parent root;
    Stage stage;
    Scene scene;


    @FXML
    private ListView<VBox> listview;

    @FXML
    private Label messageLabel;



    @FXML
    void backbutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/profile.fxml"));
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

        messageLabel.setText("Viewing "+people.getEmail()+"'s Notes");
        messageLabel.setStyle("-fx-font-size: 15;");

        listview.setCellFactory(new Callback<ListView<VBox>, ListCell<VBox>>() {
            @Override
            public ListCell<VBox> call(ListView<VBox> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(VBox item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setGraphic(item);
                        }
                    }
                };
            }
        });
        listview.setPadding(new Insets(0,0,20,0));

        try {
            Connection con1= dbConnection.getCOnnection();
            String sql="select subject,content from notes where email=?";
            PreparedStatement statement=con1.prepareStatement(sql);
            statement.setString(1, people.getEmail());
            ResultSet resultSet=statement.executeQuery();

            while (resultSet.next()){
                VBox vBox=new VBox();
                vBox.setAlignment(Pos.CENTER);
                vBox.setMaxWidth(330);

                Label label1=new Label(resultSet.getString("subject"));
                label1.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");

                Label label2=new Label(resultSet.getString("content"));
                label2.setFont(Font.font(15));
                label2.setWrapText(true);
                vBox.getChildren().add(label1);
                vBox.getChildren().add(label2);
                listview.getItems().add(vBox);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
