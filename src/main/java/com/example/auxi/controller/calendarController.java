package com.example.auxi.controller;

import com.example.auxi.Saved.currUser;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class calendarController implements Initializable {

    Stage stage;
    Scene scene;
    Parent root;

    @FXML
    private ListView<VBox> listview;

    Connection con1;
    PreparedStatement statement;

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



    @FXML
    void add(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/addCalendar.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            con1 = dbConnection.getCOnnection();
            String sql = "SELECT * FROM calendar WHERE date >= CURDATE() and email=? ORDER BY date";
            statement = con1.prepareStatement(sql);
            statement.setString(1, currUser.getEmail());
            ResultSet resultSet = statement.executeQuery();


            listview.setCellFactory(param -> new ListCell<VBox>() {
                @Override
                protected void updateItem(VBox item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        setPadding(new Insets(5, 0, 5, 0));
                        setGraphic(item);
                    }
                }
            });

            while (resultSet.next()) {
                String description = resultSet.getString("description");
                InputStream imageStream = resultSet.getBinaryStream("image");
                Date date = resultSet.getDate("date");

                VBox eventBox = new VBox();
                eventBox.setAlignment(Pos.CENTER);
                Label descriptionLabel = new Label(description);
                javafx.scene.image.Image image = new javafx.scene.image.Image(imageStream);
                javafx.scene.image.ImageView imageView = new javafx.scene.image.ImageView(image);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(200);
                Label dateLabel = new Label(date.toString());
                dateLabel.setStyle("-fx-font-size: 16;"+"-fx-text-alignment: center;");
                descriptionLabel.setStyle("-fx-font-size: 16;"+"-fx-text-alignment: center;");
                eventBox.getChildren().addAll(descriptionLabel, imageView, dateLabel);

                listview.getItems().add(eventBox);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (con1 != null) {
                    con1.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
