package ru.samsung.tcpdemo;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleTcpClient {
    private Socket clientSocket;
    private InetAddress serverAddress;

    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    private final String IP = "192.168.43.209";
    private final int PORT = 5000;

    private boolean isRunning;


    public void startClient() {
        try {
            serverAddress = InetAddress.getByName(IP);
            clientSocket = new Socket(serverAddress, PORT);
            isRunning = true;
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
            sendMessage("Hello server, from android!");

            while (isRunning) {
                String message;
                message = bufferedReader.readLine();
                Log.d(SimpleTcpClient.class.getName(), "Получен ответ от сервера: " + message);
            }
        } catch (UnknownHostException e) {
            Log.d(SimpleTcpClient.class.getName(), "Сервер недоступен: " + e.getLocalizedMessage());
        } catch (IOException e) {
            Log.d(SimpleTcpClient.class.getName(), "Ошибка подключения к серверу: " + e.getLocalizedMessage());
        }
    }

    public void stopServer() {
        isRunning = false;
        if(bufferedReader != null){
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(printWriter != null){
            printWriter.close();
        }

        try {
            clientSocket.close();
            System.out.println("Клиент завершил свою работу.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        printWriter.println(message);
        printWriter.flush();
    }
}
