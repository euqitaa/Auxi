package com.example.auxi.Saved;

import javafx.scene.image.Image;

public class currUser {
    private static String email;
    private static String userName;
    private static Image image;

    private static int points;
    public currUser(String email, String userName, Image image) {
        this.email = email;
        this.userName = userName;
        this.image = image;
    }

    public currUser(String email, String userName) {
        this.email = email;
        this.userName = userName;
    }

    public static String getEmail() {
        return email;
    }

    public static String getUserName() {
        return userName;
    }

    public static Image getImage() {
        return image;
    }

    public static void setEmail(String email) {
        currUser.email = email;
    }

    public static void setUserName(String userName) {
         currUser.userName = userName;
    }

    public static void setImage(Image image) {
        currUser.image = image;
    }

    public static void setPoints(int points) {
        currUser.points = points;
    }

    public static int getPoints() {
        return points;
    }
}
