package com.example.auxi.controller;

import com.example.auxi.Saved.newsfeedsaved;
import com.example.auxi.db.dbConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class newsFeedController implements Initializable {
    Stage stage;
    Parent root;
    Scene scene;

    private Image selectedPostImage;
    private String selectedPostContent;
    private int selectedpostId;

    @FXML
    private ListView<VBox> listview;

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
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/addfeed.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection con1 = dbConnection.getCOnnection();
            String sql = "SELECT * FROM newsfeed";
            PreparedStatement statement = con1.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String email=resultSet.getString("email");
                String imageUrl = resultSet.getString("image");
                String textContent =email+": "+resultSet.getString("caption");
                int id=resultSet.getInt("postid");

                Image image = null;
                image = loadImageFromDatabase(id);

                VBox postBox = new VBox();
                postBox.setAlignment(Pos.CENTER);
                //

                Label label=new Label(Integer.toString(id));
                label.setStyle("-fx-text-fill: white");
                postBox.getChildren().add(label);

                //
                Label contentLabel = new Label(textContent);
                contentLabel.setStyle("-fx-font-size: 16;");
                postBox.getChildren().add(contentLabel);
                ImageView imageView = new ImageView();
                if (image != null) {
                    imageView.setImage(image);
                    imageView.setFitWidth(200);
                    imageView.setPreserveRatio(true);
                    postBox.getChildren().add(imageView);
                }



                Platform.runLater(() -> listview.getItems().add(postBox));
            }

            resultSet.close();
            statement.close();
            con1.close();
        } catch (SQLException e) {
            System.err.println("Error connecting to database!");

        }


        listview.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 1) {
                VBox selectedPost = listview.getSelectionModel().getSelectedItem();
                extractImageAndContent(selectedPost);
                newsfeedsaved.setId(selectedpostId);
                newsfeedsaved.setContent(selectedPostContent);
                newsfeedsaved.setImage(selectedPostImage);
                try {
                    root = FXMLLoader.load(getClass().getResource("/com/example/auxi/comments.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    private void extractImageAndContent(VBox postBox) {
        Label label=(Label)postBox.getChildren().get(0);
        selectedpostId=Integer.parseInt(label.getText());

        ImageView imageView = (ImageView) postBox.getChildren().get(2);
        selectedPostImage = imageView.getImage();

        Label contentLabel = (Label) postBox.getChildren().get(1);
        selectedPostContent = contentLabel.getText();
    }

    private Image loadImageFromDatabase(int id) {
        try {
            Connection con = dbConnection.getCOnnection();
            String sql = "SELECT image FROM newsfeed WHERE postid = ?";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                byte[] imageData = resultSet.getBytes("image");
                return new Image(new ByteArrayInputStream(imageData));
            } else {
                System.out.println("Image not found in the database.");
            }

            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
