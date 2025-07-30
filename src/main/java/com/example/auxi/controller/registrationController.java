package com.example.auxi.controller;

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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class registrationController {

    Stage stage;
    Parent root;
    Scene scene;

    File selectedFile;


    @FXML
    private ImageView imageview;

    @FXML
    private TextField emailtextField;

    @FXML
    private TextField passwordtextField;

    @FXML
    private Label registrationLabel;

    @FXML
    void registration(ActionEvent event) throws SQLException {
        String email=emailtextField.getText();
        String password=passwordtextField.getText();

        if(email.isEmpty() || password.isEmpty() || selectedFile==null){
            registrationLabel.setText("Enter email, Password and Image");
        }

        else {
            try {
                FileInputStream fism = new FileInputStream(selectedFile);

                Connection con1 = dbConnection.getCOnnection();
                String sql = "insert into login (email,password,image) values (?,?,?)";
                PreparedStatement statement = con1.prepareStatement(sql);
                statement.setString(1, email);
                statement.setString(2, password);
                statement.setBinaryStream(3, fism, fism.available());
                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    registrationLabel.setText("Registration successful");
                } else {
                    registrationLabel.setText("username already exists");
                }} catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                registrationLabel.setText("username already exists");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                //
                Connection con1=dbConnection.getCOnnection();
                String sql2="insert into activity (email) values(?)";
                PreparedStatement statement=con1.prepareStatement(sql2);
                statement.setString(1,email);
                statement.executeUpdate();

                root = FXMLLoader.load(getClass().getResource("/com/example/auxi/login.fxml"));
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                //
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    @FXML
    void loginswitch(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/login.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void chooseImage(ActionEvent event) {
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

}
