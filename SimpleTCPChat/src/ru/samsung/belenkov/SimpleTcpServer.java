package ru.samsung.belenkov;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by USER on 13.02.2019.
 */
public class SimpleTcpServer extends Thread {
    private ServerSocket serverSocket;
    private Socket clientSocket;

    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    private final int PORT = 8080;

    private boolean isRunning;

    @Override
    public void run() {
        super.run();
        runServer();
    }

    public void runServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            isRunning = true;
            System.out.println("Сервер успешно запущен\nОжидание новых подключений...");
            clientSocket = serverSocket.accept();
            System.out.println("Зарегистрировано новое входящее подключение: " + clientSocket.getInetAddress());
            bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            printWriter = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            while (isRunning) {
                String message;
                message = bufferedReader.readLine();
                System.out.println("Получено сообщение от клиента: " + message);
                sendMessage("Echo " + message);
            }
        } catch (IOException e) {
            System.out.println("Ошибка запуска сервера " + e);
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
            serverSocket.close();
            System.out.println("Сервер завершил свою работу.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        printWriter.println(message);
        printWriter.flush();
    }
}
