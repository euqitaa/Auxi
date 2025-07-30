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
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class note2Controller {
    Stage stage;
    Scene scene;
    Parent root;

    @FXML
    private TextField noteContent;

    @FXML
    private TextField noteSubject;

    @FXML
    private Label messageLabel;



    @FXML
    void backbutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/notes.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void saveButton(ActionEvent event) throws SQLException {
        String content = noteContent.getText();
        String email = currUser.getEmail();
        String subject = noteSubject.getText();

        if (!content.isEmpty() && !subject.isEmpty()) {
            Connection con1 = dbConnection.getCOnnection();
            String sql = "insert into notes (email,subject,content) values(?,?,?)";
            PreparedStatement statement = con1.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, subject);
            statement.setString(3, content);
            statement.executeUpdate();
            increase.increasepoints(1);
            try {
                backbutton(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            messageLabel.setText("Please Enter Subject and content");
        }
    }

}
