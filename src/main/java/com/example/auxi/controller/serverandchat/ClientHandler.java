package com.example.auxi.controller.serverandchat;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    private String username;
    private Server server;

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.username = bufferedReader.readLine();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = bufferedReader.readLine();
                if (msg == null) {
                    System.out.println(username + " disconnected.");
                    server.clients.remove(username);
                    break;
                }
                String[] parts = msg.split(":");
                String clientName = parts[0];
                String messageContent = parts[1];
                server.broadcast(clientName, messageContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                closeEverything(socket, bufferedReader, bufferedWriter);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) throws IOException {
        if (socket != null) {
            socket.close();
        }
        if (bufferedReader != null) {
            bufferedReader.close();
        }
        if (bufferedWriter != null) {
            bufferedWriter.close();
        }
    }
}
