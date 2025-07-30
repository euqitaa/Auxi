package com.example.auxi.controller.serverandchat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private ServerSocket serverSocket;
    static ConcurrentHashMap<String, ClientHandler> clients;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
        clients = new ConcurrentHashMap<>();
    }

    public void startServer() throws IOException {
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            System.out.println("New client connected.");
            ClientHandler clientHandler = new ClientHandler(socket, this);
            clients.put(clientHandler.getUsername(), clientHandler);
            new Thread(clientHandler).start();
        }
    }

    public void broadcast(String clientName, String messageContent) throws IOException {
        for (ClientHandler clientHandler : clients.values()) {
            if (clientHandler.getUsername().equals(clientName)) { // Exclude sender
                clientHandler.bufferedWriter.write(messageContent);
                clientHandler.bufferedWriter.newLine();
                clientHandler.bufferedWriter.flush();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        Server server = new Server(new ServerSocket(6000));
        server.startServer();
    }

}

