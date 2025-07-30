package com.example.auxi.controller;

import com.example.auxi.Saved.currUser;
import com.example.auxi.Saved.loadProfile;
import com.example.auxi.Saved.people;
import com.example.auxi.db.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class profileController implements Initializable {
    Stage stage;
    Scene scene;
    Parent root;

    @FXML
    private Label message;

    Connection con1;
    PreparedStatement statement;

    @FXML
    private Label creditsLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Button follow;

    @FXML
    private Label followersLabel;

    @FXML
    private Label followingLabel;

    @FXML
    private Label notesLabel;

    @FXML
    private ImageView userimage;


    @FXML
    void followAction(ActionEvent event) {
        if (follow.getText().equals("Follow")) {
            try {
                con1 = dbConnection.getCOnnection();
                String sql = "insert into followers (follower_email,following_email) values(?,?)";
                statement = con1.prepareStatement(sql);
                statement.setString(1, currUser.getEmail());
                statement.setString(2, loadProfile.getEmail());
                statement.executeUpdate();
                checkfollow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try {
                con1 = dbConnection.getCOnnection();
                String sql = "delete from followers where follower_email=? and following_email=? ";
                statement = con1.prepareStatement(sql);
                statement.setString(1, currUser.getEmail());
                statement.setString(2, loadProfile.getEmail());
                statement.executeUpdate();
                checkfollow();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @FXML
    void backbutton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/peopleandranking.fxml"));
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


    private void getCredits() {
        try {
            con1 = dbConnection.getCOnnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String sql = "select credit from login where email=?";
            statement = con1.prepareStatement(sql);
            statement.setString(1, loadProfile.getEmail());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                creditsLabel.setText(resultSet.getString("count(*)"));
            } else {
                creditsLabel.setText("0");
            }
        } catch (Exception e) {
            creditsLabel.setText("0");
        }
    }

    private void getNotesfromDatabase() {
        try {
            con1 = dbConnection.getCOnnection();
            String sql = "select count(*) from notes where email=?";
            statement = con1.prepareStatement(sql);
            statement.setString(1, loadProfile.getEmail());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                notesLabel.setText(resultSet.getString("count(*)"));
            } else {
                notesLabel.setText("0");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void getNotes(ActionEvent event) throws IOException {
        if(follow.getText().equals("Follow")){
            message.setText("You are not Following this Person");
        }
        else {
            people.setEmail(loadProfile.getEmail());
            root = FXMLLoader.load(getClass().getResource("/com/example/auxi/loadNotes.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    private void getfollowing() {
        try {
            con1 = dbConnection.getCOnnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String sql = "select count(*) from followers where follower_email=?";
            statement = con1.prepareStatement(sql);
            statement.setString(1, loadProfile.getEmail());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                followingLabel.setText(resultSet.getString("count(*)"));
            } else {
                followingLabel.setText("0");
            }
        } catch (Exception e) {
            followingLabel.setText("0");
        }
    }

    private void getfollowers() {
        try {
            con1 = dbConnection.getCOnnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            String sql = "select count(*) from followers where following_email=?";
            statement = con1.prepareStatement(sql);
            statement.setString(1, loadProfile.getEmail());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                followersLabel.setText(resultSet.getString("count(*)"));
            } else {
                followersLabel.setText("0");
            }
        } catch (Exception e) {
            followersLabel.setText("0");
        }
    }

    private void getImages() {
        Image image=null;
        try {
            con1=dbConnection.getCOnnection();
            String sql="select image from login where email=?";
            statement=con1.prepareStatement(sql);
            statement.setString(1,loadProfile.getEmail());
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()){

                Blob blob = resultSet.getBlob("image");
                if (blob != null) {
                    try (InputStream inputStream = blob.getBinaryStream()) {
                        image = new Image(inputStream);
                    }
                }
            }
            userimage.setImage(image);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailLabel.setText(loadProfile.getEmail());
        getImages();
        getfollowers();
        getfollowing();
        getNotesfromDatabase();
        getCredits();
        checkfollow();
    }

    private void checkfollow() {
        try {
            con1 = dbConnection.getCOnnection();
            String sql = "select * from followers where follower_email=? and following_email=?";
            statement = con1.prepareStatement(sql);
            statement.setString(1, currUser.getEmail());
            statement.setString(2, loadProfile.getEmail());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                follow.setText("Unfollow");
            } else {
                follow.setText("Follow");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
