package com.example.auxi.controller;

import com.example.auxi.Saved.currUser;
import com.example.auxi.Saved.increase;
import com.example.auxi.Saved.newsfeedsaved;
import com.example.auxi.db.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class commentsController implements Initializable {
    Stage stage;
    Scene scene;
    Parent root;

    @FXML
    private Label ContentLabel;

    @FXML
    private TextField comment;

    @FXML
    private ListView<String> commetslistview;

    @FXML
    private ImageView imageview;

    @FXML
    void backbutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/newsfeed.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void chatbutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/chat.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void homebutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/home.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void menu(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/user.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void people(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/peopleandranking.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void post(ActionEvent event) {
        String comments=comment.getText();
        int id=newsfeedsaved.getId();
        String email= currUser.getEmail();
        try {
            Connection con1= dbConnection.getCOnnection();
            String sql="insert into replytable (postid,email,reply) values(?,?,?)";
            PreparedStatement statement=con1.prepareStatement(sql);
            statement.setInt(1,id);
            statement.setString(2,email);
            statement.setString(3,comments);
            statement.executeUpdate();
            increase.increasepoints(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        commetslistview.getItems().add(email+": "+comments);
        comment.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContentLabel.setText(newsfeedsaved.getContent());
        imageview.setImage(newsfeedsaved.getImage());
        imageview.preserveRatioProperty();

        //
        commetslistview.setCellFactory(list -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item);
                    setFont(Font.font("Arial", 16)); // Adjust font size as needed
                }
            }
        });
        //

        try {
            Connection con1= dbConnection.getCOnnection();
            String sql="select email,reply from replytable where postid=?";
            PreparedStatement statement=con1.prepareStatement(sql);
            statement.setInt(1,newsfeedsaved.getId());
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                String comments;
                comments=resultSet.getString("email")+": "+resultSet.getString("reply");
                commetslistview.getItems().add(comments);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void largerImage(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/largerimage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
