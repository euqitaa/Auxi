package com.example.auxi.controller;

import com.example.auxi.Saved.currUser;
import com.example.auxi.Saved.loadProfile;
import com.example.auxi.Saved.userdto;
import com.example.auxi.db.dbConnection;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class peopleAndRankingController implements Initializable {
    Stage stage;
    Parent root;
    Scene scene;


    @FXML
    private Button globalButton;

    private ObservableList<userdto> ob;

    @FXML
    private TableView<userdto> table;

    @FXML
    private TableColumn<userdto, Integer> pointsColumn;

    @FXML
    private TableColumn<userdto, String> emailColumn;

    @FXML
    void FriendsButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/peopleandranking.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleGlobalButton(ActionEvent event) {
        try {
            fetchAllUsersFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchAllUsersFromDatabase() throws SQLException {
        ob.clear();
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            con = dbConnection.getCOnnection();
            String sql = "SELECT email, credit FROM login ORDER BY credit DESC";
            statement = con.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if(!resultSet.getString("email").equals(currUser.getEmail())) {
                    String email = resultSet.getString("email");
                    int points = resultSet.getInt("credit");
                    ob.add(new userdto(email, points));
                }
            }
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pointsColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPoints()).asObject());
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));


        //
        pointsColumn.setCellFactory(column -> {
            return new TableCell<userdto, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(item));
                        setFont(Font.font("Arial", 16)); // Adjust font size as needed
                    }
                }
            };
        });

        emailColumn.setCellFactory(column -> {
            return new TableCell<userdto, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item);
                        setFont(Font.font("Arial", 16)); // Adjust font size as needed
                    }
                }
            };
        });

        //
        ob= FXCollections.observableArrayList();
        try {
            fetchDataFromDatabase();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        table.setItems(ob);

        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (table.getSelectionModel().getSelectedItem() != null) {
                    try {
                        loadProfile.setEmail(table.getSelectionModel().getSelectedItem().getEmail());
                        root = FXMLLoader.load(getClass().getResource("/com/example/auxi/profile.fxml"));
                        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        // Handle IOException
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void fetchDataFromDatabase() throws SQLException {
        Connection con1=dbConnection.getCOnnection();
        //
        String sql="select email,credit from login where email in(select following_email from followers where follower_email=?)";
        PreparedStatement statement=con1.prepareStatement(sql);
        statement.setString(1,currUser.getEmail());
        //
        ResultSet resultSet=statement.executeQuery();

        while (resultSet.next()){
            if(!resultSet.getString("email").equals(currUser.getEmail())) {
                String email = resultSet.getString("email");
                int points = resultSet.getInt("credit");
                ob.add(new userdto(email, points));
            }
        }
    }
}
