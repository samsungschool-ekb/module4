package controller.network;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static utils.Command.SERVER_AUTHORIZE;
import static utils.Error.USER_DISCONNECT;

/**
 * Created by USER on 20.02.2019.
 */
public class ClientConnection extends Thread {

    private String userName;

    private Socket clientSocket;

    private BufferedReader in;

    private PrintWriter out;

    private ArrayList<String> nameList;

    private ArrayList<PrintWriter> userWriterList;

    private boolean isRunning;

    public ClientConnection(Socket clientSocket, ArrayList<String> nameList, ArrayList<PrintWriter> userWriterList) {
        this.clientSocket = clientSocket;
        this.nameList = nameList;
        this.userWriterList = userWriterList;
    }

    private void startClientConnection() {
        isRunning = true;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
            authorizeUser();
            receiveMessage();
        } catch (IOException e) {
            System.out.println(USER_DISCONNECT);
        } finally {
            stopClientConnection();
        }
    }

    private void authorizeUser() throws IOException {
        System.out.println("Ожидание авторизации пользователя");
        sendMessage(SERVER_AUTHORIZE);
        while (true) {
            userName = in.readLine();
            if (userName == null) {
                return;
            }
            synchronized (nameList) {
                nameList.add(userName);
            }
            synchronized (userWriterList) {
                userWriterList.add(out);
                break;
            }
        }
        System.out.println("Пользователь с именем " + userName + " успешно авторизован");
    }

    private void stopClientConnection() {
        if (userName != null) {
            userWriterList.remove(userName);
        }
        if (out != null) {
            userWriterList.remove(out);
        }

        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        out.println(message);
        out.flush();
    }

    private void receiveMessage() throws IOException {
        while (isRunning) {
            String clientMessage = in.readLine();
            if (clientMessage == null) {
                return;
            }
            for (PrintWriter out : userWriterList) {
                out.println(userName + ":" + clientMessage);
                out.flush();
            }
        }
    }

    @Override
    public void run() {
        startClientConnection();
    }
}
