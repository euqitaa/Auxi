package com.example.auxi.controller;

import com.example.auxi.Saved.currUser;
import com.example.auxi.db.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class userController implements Initializable {

    @FXML
    private ImageView userimage;


    @FXML
    private Label creditsLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label followersLabel;

    @FXML
    private Label followingLabel;

    @FXML
    private Label notesLabel;


    Stage stage;
    Parent root;
    Scene scene;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailLabel.setText(currUser.getEmail());
        emailLabel.setWrapText(true);
        userimage.setImage(currUser.getImage());
        creditsLabel.setText(Integer.toString(currUser.getPoints()));
        loadNotes();
        loadfollowers();
        loadfollowing();
    }

    private void loadNotes(){
        try {
            con1= dbConnection.getCOnnection();
            String sql="select count(*) from notes where email=?";
            statement=con1.prepareStatement(sql);
            statement.setString(1,currUser.getEmail());
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()){
                notesLabel.setText(resultSet.getString("count(*)"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadfollowers(){
        try {
            con1= dbConnection.getCOnnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            String sql="select count(*) from followers where following_email=?";
            statement=con1.prepareStatement(sql);
            statement.setString(1,currUser.getEmail());
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()){
                followersLabel.setText(resultSet.getString("count(*)"));
            }
            else{
                followersLabel.setText("0");
            }
        }catch (Exception e){
            followersLabel.setText("0");
        }

    }

    private void loadfollowing(){
        try {
            con1= dbConnection.getCOnnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String sql="select count(*) from followers where follower_email=?";
            statement=con1.prepareStatement(sql);
            statement.setString(1,currUser.getEmail());
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()){
                followingLabel.setText(resultSet.getString("count(*)"));
            }
            else{
                followingLabel.setText("0");
            }
        }catch (Exception e){
            followingLabel.setText("0");
        }
    }

    @FXML
    void logout(ActionEvent event) throws IOException {

        try {
            Connection con1= dbConnection.getCOnnection();
            String sql="UPDATE activity set status=0 WHERE email=?";
            PreparedStatement statement=con1.prepareStatement(sql);
            statement.setString(1, currUser.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/login.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deleteAccount(ActionEvent event) throws IOException {
        deletelogin();
        deletenotes();
        deletefollowers();
        deletefollowing();
        deletemessages();
        deletenewsfeed();
        deletereply();
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/login.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private  void deletelogin(){
        try {
            con1= dbConnection.getCOnnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String sql="delete from login where email=?";
            statement=con1.prepareStatement(sql);
            statement.setString(1,currUser.getEmail());
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void deletenotes(){
        try {
            con1= dbConnection.getCOnnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String sql="delete from notes where email=?";
            statement=con1.prepareStatement(sql);
            statement.setString(1,currUser.getEmail());
            statement.executeUpdate();;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void deletefollowers(){
        try {
            con1= dbConnection.getCOnnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String sql="delete from followers where following_email=?";
            statement=con1.prepareStatement(sql);
            statement.setString(1,currUser.getEmail());
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void deletefollowing(){
        try {
            con1= dbConnection.getCOnnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String sql="delete from followers where follower_email=?";
            statement=con1.prepareStatement(sql);
            statement.setString(1,currUser.getEmail());
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void deletereply(){
        try {
            con1= dbConnection.getCOnnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String sql="delete from replytable where email=?";
            statement=con1.prepareStatement(sql);
            statement.setString(1,currUser.getEmail());
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void deletemessages(){
        try {
            con1= dbConnection.getCOnnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String sql="delete from messages where receiver=?";
            statement=con1.prepareStatement(sql);
            statement.setString(1,currUser.getEmail());
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }


        try {
            con1= dbConnection.getCOnnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String sql="delete from messages where sender=?";
            statement=con1.prepareStatement(sql);
            statement.setString(1,currUser.getEmail());
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void deletenewsfeed(){
        try {
            con1= dbConnection.getCOnnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            String sql="delete from newsfeed where email=?";
            statement=con1.prepareStatement(sql);
            statement.setString(1,currUser.getEmail());
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
