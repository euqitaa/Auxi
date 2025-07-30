package com.example.auxi.controller;

import com.example.auxi.Saved.currUser;
import com.example.auxi.db.dbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class srController implements Initializable {
    Stage stage;
    Parent root;
    Scene scene;

    @FXML
    private Label todayGroup;
    @FXML
    private HBox TodayTopics;


    @FXML
    private TilePane topicList;

    


    @FXML
    void addAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/sradd.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

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
    
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String c_email = currUser.getEmail();
            Connection con1 = dbConnection.getCOnnection();
            PreparedStatement statement = con1.prepareStatement("SELECT * FROM spacedrep WHERE email = ?");
            statement.setString(1, c_email);
            ResultSet resultSet = statement.executeQuery();

            DayOfWeek currentDay = LocalDate.now().getDayOfWeek();
            LocalDate currentDate = LocalDate.now();
            boolean fridayMessageAdded = false;
            while (resultSet.next()){
                int dayGroup =resultSet.getInt("DayGroup");
                String topicName =resultSet.getString("TopicName");
                int dayCount = resultSet.getInt("DayCount");
                LocalDate lastUpdateDate = resultSet.getDate("LastUpdateDate").toLocalDate();
                if(currentDay == DayOfWeek.FRIDAY && !fridayMessageAdded) {
                    todayGroup.setText("Today's Group: FRIDAY");
                    Label label = new Label("TAKE REST TODAY :>");
                    label.setMinHeight(200);
                    label.setMinWidth(200);
                    label.setAlignment(Pos.CENTER);
                    label.setStyle(" -fx-border-color: black; -fx-text-alignment: center; -fx-border-width: 1; -fx-border-radius: 0.5em");
                    topicList.getChildren().add(label);
                     fridayMessageAdded = true;
                }
                else if(currentDay == DayOfWeek.SATURDAY || currentDay == DayOfWeek.MONDAY || currentDay == DayOfWeek.WEDNESDAY && lastUpdateDate.isBefore(currentDate)){
                    if (dayGroup == 1) {
                        todayGroup.setText("Today's Group: SAT / MON / WED");
                        Label contentLabel = new Label(topicName);
                        contentLabel.setMinHeight(80);
                        contentLabel.setMinWidth(100);
                        contentLabel.setAlignment(Pos.CENTER);
                        contentLabel.setStyle(" -fx-border-color: black; -fx-text-alignment: center; -fx-border-width: 1; -fx-border-radius: 0.5em");
                        topicList.getChildren().add(contentLabel);
                        if (dayCount > 3) {
                            PreparedStatement deleteStatement = con1.prepareStatement("DELETE FROM spacedrep WHERE email = ? AND TopicName = ?");
                            deleteStatement.setString(1, c_email);
                            deleteStatement.setString(2, topicName);
                            deleteStatement.executeUpdate();
                            deleteStatement.close();
                        } else if(lastUpdateDate.isBefore(currentDate)) {
                            // Increment DayCount
                            PreparedStatement updateStatement = con1.prepareStatement("UPDATE spacedrep SET DayCount = ?, LastUpdateDate = ? WHERE email = ? AND TopicName = ?");
                            updateStatement.setInt(1, dayCount + 1);
                            updateStatement.setDate(2, java.sql.Date.valueOf(currentDate));
                            updateStatement.setString(3, c_email);
                            updateStatement.setString(4, topicName);
                            updateStatement.executeUpdate();
                            updateStatement.close();
                        }
                    }
                }
                else if (currentDay == DayOfWeek.SUNDAY || currentDay == DayOfWeek.TUESDAY || currentDay == DayOfWeek.THURSDAY && lastUpdateDate.isBefore(currentDate)){
                    if(dayGroup == 2) {
                        todayGroup.setText("Today's Group: SUN / TUE / THURS");
                        Label contentLabel = new Label(topicName);
                        contentLabel.setMinHeight(80);
                        contentLabel.setMinWidth(100);
                        contentLabel.setAlignment(Pos.CENTER);
                        contentLabel.setStyle(" -fx-border-color: black; -fx-text-alignment: center; -fx-border-width: 1; -fx-border-radius: 0.5em");
                        topicList.getChildren().add(contentLabel);
                        if (dayCount > 3) {
                            PreparedStatement deleteStatement = con1.prepareStatement("DELETE FROM spacedrep WHERE email = ? AND TopicName = ?");
                            deleteStatement.setString(1, c_email);
                            deleteStatement.setString(2, topicName);
                            deleteStatement.executeUpdate();
                            deleteStatement.close();
                        } else if(lastUpdateDate.isBefore(currentDate)){
                            // Increment DayCount
                            PreparedStatement updateStatement = con1.prepareStatement("UPDATE spacedrep SET DayCount = ?, LastUpdateDate = ? WHERE email = ? AND TopicName = ?");
                            updateStatement.setInt(1, dayCount + 1);
                            updateStatement.setDate(2, java.sql.Date.valueOf(currentDate));
                            updateStatement.setString(3, c_email);
                            updateStatement.setString(4, topicName);
                            updateStatement.executeUpdate();
                            updateStatement.close();
                        }

                    }
                }


            }

            resultSet.close();
            statement.close();
            con1.close();
        } catch (SQLException e) {
            System.err.println("Error connecting to database!");

        }
    }
    }

