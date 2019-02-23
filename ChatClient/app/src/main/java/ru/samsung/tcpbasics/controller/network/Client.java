package ru.samsung.tcpbasics.controller.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;


import static ru.samsung.tcpbasics.controller.utils.Network.IP;
import static ru.samsung.tcpbasics.controller.utils.Network.PORT;


public class Client {

    private Socket clientSocket;

    private InetAddress serverAddress;

    private BufferedReader in;

    private PrintWriter out;

    private boolean isRunning;

    public Observable<String> receiveMessage() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                while (isRunning) {
                    String message = in.readLine();
                    emitter.onNext(message);
                }
                emitter.onComplete();
            }
        });
    }

    public Single<Boolean> sendMessage(final String message) {
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(SingleEmitter<Boolean> emitter) {
                System.out.println("Отправка сообщения");
                out.println(message);
                out.flush();
                emitter.onSuccess(true);
            }
        });
    }

    public Single<Boolean> startClient() {
        return Single.create(new SingleOnSubscribe<Boolean>() {
            @Override
            public void subscribe(SingleEmitter<Boolean> emitter) {
                try {
                    serverAddress = InetAddress.getByName(IP);
                    clientSocket = new Socket(serverAddress, PORT);
                    isRunning = true;
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
                    emitter.onSuccess(true);
                } catch (UnknownHostException e) {
                    Log.e(Client.class.getName(), "не удалось подключиться к серверу " + e.getLocalizedMessage());
                } catch (IOException e) {
                    Log.e(Client.class.getName(), "ошибка соединения " + e.getLocalizedMessage());
                }
            }
        });
    }

    public Single<Void> stopServer() {
        return Single.create(new SingleOnSubscribe<Void>() {
            @Override
            public void subscribe(SingleEmitter<Void> emitter) throws Exception {
                isRunning = false;
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (out != null) {
                    out.close();
                }

                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                        Log.d(Client.class.getName(), "Клиент успешно завершил работу");
                    } catch (IOException e) {
                        Log.e(Client.class.getName(), "" + e.getLocalizedMessage());
                    }
                }
            }
        });
    }
}
