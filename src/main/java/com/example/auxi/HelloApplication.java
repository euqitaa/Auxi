package com.example.auxi;

import com.example.auxi.Saved.currUser;
import com.example.auxi.controller.loadingController;
import com.example.auxi.db.dbConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/auxi/loading.fxml"));
        Parent root = loader.load();
        loadingController controller = loader.getController();
        controller.setStage(primaryStage);
        Image image=new Image("mobile-app.png");
        primaryStage.getIcons().add(image);
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);

        
        // Add onCloseRequest event handler
        primaryStage.setOnCloseRequest(event -> {
            try {
                Connection con1= dbConnection.getCOnnection();
                String sql="UPDATE activity set status=0 WHERE email=?";
                PreparedStatement statement=con1.prepareStatement(sql);
                statement.setString(1, currUser.getEmail());
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
