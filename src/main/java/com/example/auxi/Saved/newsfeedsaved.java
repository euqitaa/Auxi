package com.example.auxi.Saved;

import javafx.scene.image.Image;

public class newsfeedsaved {
    public static String content;
    public static Image image;
    public static int id;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        newsfeedsaved.id = id;
    }

    public static String getContent() {
        return content;
    }

    public static void setContent(String content) {
        newsfeedsaved.content = content;
    }

    public static Image getImage() {
        return image;
    }

    public static void setImage(Image image) {
        newsfeedsaved.image = image;
    }
}
