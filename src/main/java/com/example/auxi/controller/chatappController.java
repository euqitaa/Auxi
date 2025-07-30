package com.example.auxi.controller;

import com.example.auxi.Saved.currUser;
import com.example.auxi.Saved.people;
import com.example.auxi.controller.serverandchat.Client;
import com.example.auxi.db.dbConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class chatappController implements Initializable{
    Stage stage;
    Parent root;
    Scene scene;

    @FXML
    private TextField msgtextField;



    @FXML
    private Label activityLabel;

    @FXML
    private Button sendButton;


    @FXML
    private ListView<Label> l_view;

    Client client;

    @FXML
    private Label emailLabel;

    @FXML
    private ImageView imageview;



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

        //image set
        try {
            Connection con1= dbConnection.getCOnnection();
            String sql="select image from login where email=?";
            PreparedStatement statement=con1.prepareStatement(sql);
            statement.setString(1,people.getEmail());
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()) {
                InputStream imageStream = resultSet.getBinaryStream("image");
                Image image = new Image(imageStream);
                imageview.setImage(image);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //





        //activity check
        try {
            Connection con1= dbConnection.getCOnnection();
            String sql="select status from activity where email=?";
            PreparedStatement statement=con1.prepareStatement(sql);
            statement.setString(1,people.getEmail());
            ResultSet resultSet=statement.executeQuery();
            if(resultSet.next()){
                int res=resultSet.getInt("status");
                System.out.println(res);
                if(res==1){
                    activityLabel.setStyle("-fx-text-fill: Green;"+"-fx-font-size: 16");
                }
                else {
                    activityLabel.setStyle("-fx-text-fill: Red;"+"-fx-font-size: 16");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //


        //
        emailLabel.setText(people.getEmail());
        //chat history
        try {
            Connection con1= dbConnection.getCOnnection();
            String recive=currUser.getEmail();
            String send=people.getEmail();
            String sql="select message from messages where (sender=? and receiver=?) or (sender=? and receiver=?)";
            PreparedStatement statement=con1.prepareStatement(sql);
            statement.setString(1,recive);
            statement.setString(2,send);
            statement.setString(3,send);
            statement.setString(4,recive);
            ResultSet resultSet=statement.executeQuery();

            while (resultSet.next()){
                Label label=new Label(resultSet.getString("message"));
                label.setStyle("-fx-font-size: 16;");
                l_view.getItems().add(label);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //if any error occurs in future rollback to any version before 29th april 5pm

        try {
            client = new Client(new Socket("127.0.0.1", 6000), currUser.getEmail());
        } catch (Exception e) {
            System.out.println("Failed creating Client");
        }



        if (client != null) {
            client.receive(l_view);

            sendButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String msg = msgtextField.getText();
                    Label label=new Label(currUser.getEmail()+": "+msg);
                    label.setAlignment(Pos.CENTER_RIGHT);
                    label.setStyle("-fx-font-size: 16;");
                    if (!msg.isEmpty()) {
                        l_view.getItems().add(label);
                        //
                        loadmessageintodatabase(currUser.getEmail()+": "+msg,currUser.getEmail(),people.getEmail());
                        //
                        try {
                            client.send(people.getEmail(), msg);
                            msgtextField.clear();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }



    }


    public static void addlabel(String message,ListView listView) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label label=new Label(people.getEmail()+": "+message);
                label.setStyle("-fx-font-size: 16");
                listView.getItems().add(label);
                System.out.println("Message added to UI: " + message);
            }
        });
    }


    //
    private void loadmessageintodatabase(String message,String sender,String receiver){
        try {
            Connection con1=dbConnection.getCOnnection();
            String sql="insert into messages (sender,receiver,message) values(?,?,?)";
            PreparedStatement statement=con1.prepareStatement(sql);
            statement.setString(1,sender);
            statement.setString(2,receiver);
            statement.setString(3,message);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //

}
