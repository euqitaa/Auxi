package com.example.auxi.controller;

import com.example.auxi.Saved.currUser;
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
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginController {

    Stage stage;
    Parent root;
    Scene scene;

    @FXML
    private Label messagelabel;

    @FXML
    private TextField emailtextField;

    @FXML
    private TextField passwordtextField;

    @FXML
    void registerAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/registration.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void singinAction(ActionEvent event) throws IOException, SQLException {

        String email=emailtextField.getText();
        String password=passwordtextField.getText();
        //Connection con1=DriverManager.getConnection("jdbc:mysql://localhost:3306/auxidb","root","");

        Connection con1=dbConnection.getCOnnection();
        String sql="select * from login where email=? and password=?";
        PreparedStatement statement=con1.prepareStatement(sql);
        statement.setString(1,email);
        statement.setString(2,password);
        ResultSet row=statement.executeQuery();
        if(row.next()){
            currUser.setEmail(email);
            currUser.setPoints(row.getInt("credit"));

            //
            try {
                Connection con2= dbConnection.getCOnnection();
                String sql2="UPDATE activity set status=1 WHERE email=?";
                PreparedStatement statements=con2.prepareStatement(sql2);
                statements.setString(1, currUser.getEmail());
                statements.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            //

            //loading user image from currUser
            byte[] imageData = row.getBytes("image");
            currUser.setImage(new Image(new ByteArrayInputStream(imageData)));
            //currUser.setImage((Image) row.getBlob("image"));
            //
            root = FXMLLoader.load(getClass().getResource("/com/example/auxi/home.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            messagelabel.setText("Wrong Credentials");
        }

    }

}
