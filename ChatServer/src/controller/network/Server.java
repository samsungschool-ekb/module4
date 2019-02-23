package controller.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static utils.Network.PORT;

/**
 * Created by USER on 20.02.2019.
 */
public class Server extends Thread {
    private ServerSocket serverSocket;

    private ArrayList<String> nameList;

    private ArrayList<PrintWriter> userWriterList;

    private boolean isRunning = true;

    private void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            nameList = new ArrayList<>();
            userWriterList = new ArrayList<>();
            isRunning = true;
            System.out.println("Сервер запущен: " + serverSocket);
            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                ClientConnection clientConnection = new ClientConnection(clientSocket, nameList, userWriterList);
                clientConnection.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        startServer();
    }
}
