package com.example.auxi.Saved;

public class people {
    private static String email;

    public people(String email) {
        this.email = email;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        people.email = email;
    }
}
