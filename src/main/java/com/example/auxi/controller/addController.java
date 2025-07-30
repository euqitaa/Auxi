package com.example.auxi.controller;

import com.example.auxi.Saved.currUser;
import com.example.auxi.Saved.increase;
import com.example.auxi.db.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class addController {

    Stage stage;
    Parent root;
    Scene scene;

    @FXML
    private Label message;

    @FXML
    private TextField content;

    @FXML
    private ImageView imageview;
    File selectedFile;

    @FXML
    void imagechoose(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageview.setImage(image);
        }
    }


    @FXML
    void post(ActionEvent event) throws IOException {
        String contents = this.content.getText();
        String email = currUser.getEmail();

        try {
            Connection con = dbConnection.getCOnnection();
            if(selectedFile!=null) {
                FileInputStream fism = new FileInputStream(selectedFile);
                PreparedStatement statement = con.prepareStatement("INSERT INTO newsfeed (caption, image,email) VALUES (?, ?,?)");
                statement.setString(1, contents);
                statement.setBinaryStream(2, fism, fism.available());
                statement.setString(3, email);
                statement.executeUpdate();
                increase.increasepoints(10);
                root = FXMLLoader.load(getClass().getResource("/com/example/auxi/newsfeed.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
            else{
                message.setText("Please select an Image");
            }
        }catch (Exception e){
            message.setText("Please select an Image");
            e.printStackTrace();
        }

    }


    @FXML
    void back(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/newsfeed.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
