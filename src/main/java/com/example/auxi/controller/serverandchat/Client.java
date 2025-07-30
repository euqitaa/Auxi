package com.example.auxi.controller.serverandchat;

import com.example.auxi.controller.chatappController;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String name;

    public Client(Socket socket, String name) throws IOException {
        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bufferedWriter.write(name);
        bufferedWriter.newLine();
        bufferedWriter.flush();
        this.name = name;
    }

    public void send(String toUser, String message) throws IOException {
        String fullMessage = toUser + ": " + message;
        bufferedWriter.write(fullMessage);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    public void listenForMessages() throws IOException {
        new Thread(() -> {
            try {
                while (true) {
                    String message = bufferedReader.readLine();
                    if (message != null) {
                        System.out.println("Received: " + message);
                    } else {
                        System.out.println("Connection closed by the server.");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void receive(ListView listView){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket.isConnected()){
                    try {
                        String msg=bufferedReader.readLine();
                        chatappController.addlabel(msg,listView);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }).start();
    }

    /*
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        Client client = new Client(new Socket("localhost", 6000), name);
        client.listenForMessages();
        while (true) {
            System.out.print("To: ");
            String to = sc.nextLine();
            System.out.print("Message: ");
            String msg = sc.nextLine();
            client.send(to, msg);
        }
    }
    */
}
