package ru.samsung.tcpbasics.controller.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ru.samsung.tcpbasics.R;
import ru.samsung.tcpbasics.controller.adapter.ChatRecyclerAdapter;
import ru.samsung.tcpbasics.controller.model.Message;
import ru.samsung.tcpbasics.controller.network.Client;
import ru.samsung.tcpbasics.controller.utils.Commands;


public class MainActivity extends AppCompatActivity {

    private RecyclerView chatRecyclerView;

    private RecyclerView.LayoutManager chatRecyclerLayoutManager;

    private EditText chatMessageEditText;

    private ImageButton messageSendButton;

    private RecyclerView.Adapter chatRecyclerAdapter;

    private ArrayList<Message> messageArrayList;

    private Client client;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initAdapter();

        client = new Client();

        client.startClient()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean connect) throws Exception {
                        if (connect) {
                            client.receiveMessage()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<String>() {
                                        @Override
                                        public void accept(String s) throws Exception {
                                            switch (s) {
                                                case Commands.SERVER_AUTH_COMMAND:
                                                    initNameDialog()
                                                            .subscribeOn(AndroidSchedulers.mainThread())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .subscribe(new Consumer<String>() {
                                                                @Override
                                                                public void accept(String s) {
                                                                    client.sendMessage(s)
                                                                            .subscribeOn(Schedulers.io())
                                                                            .observeOn(AndroidSchedulers.mainThread())
                                                                            .subscribe(new Consumer<Boolean>() {
                                                                                @Override
                                                                                public void accept(Boolean aBoolean) throws Exception {
                                                                                    System.out.println("Пользователь авторизован");
                                                                                    messageSendButton.setEnabled(true);
                                                                                    chatMessageEditText.setEnabled(true);
                                                                                    messageSendButton.setOnClickListener(new View.OnClickListener() {
                                                                                        @Override
                                                                                        public void onClick(View v) {
                                                                                            client.sendMessage(chatMessageEditText.getText().toString())
                                                                                                    .subscribeOn(Schedulers.io())
                                                                                                    .observeOn(AndroidSchedulers.mainThread())
                                                                                                    .subscribe(new Consumer<Boolean>() {
                                                                                                        @Override
                                                                                                        public void accept(Boolean aBoolean) {
                                                                                                            System.out.println("Сообщение отправлено");
                                                                                                        }
                                                                                                    });
                                                                                            chatMessageEditText.setText("");
                                                                                        }
                                                                                    });
                                                                                }
                                                                            });
                                                                }
                                                            });
                                                    break;
                                                default:
                                                    System.out.println("received: " + s);
                                                    messageArrayList.add(new Message("", "", s));
                                                    chatRecyclerAdapter.notifyItemInserted(messageArrayList.size());
                                                    break;
                                            }
                                        }
                                    });
                        } else {
                            System.out.println("Подключиться не удалось");
                        }
                    }
                });
    }


    private void initUI() {
        chatMessageEditText = findViewById(R.id.chatMessageEditText);
        messageSendButton = findViewById(R.id.messageSendButton);
        chatRecyclerView = findViewById(R.id.chatMessagesRecycler);

        chatMessageEditText.setEnabled(false);
        messageSendButton.setEnabled(false);
    }

    private void initAdapter() {
        messageArrayList = new ArrayList<>();
        chatRecyclerLayoutManager = new LinearLayoutManager(this);
        chatRecyclerAdapter = new ChatRecyclerAdapter(messageArrayList);

        chatRecyclerView.setLayoutManager(chatRecyclerLayoutManager);
        chatRecyclerView.setAdapter(chatRecyclerAdapter);
    }

    private Single<String> initNameDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);

        final View nameDialogView = inflater.inflate(R.layout.name_dialog_view, null);
        final EditText dialogNameEdit = nameDialogView.findViewById(R.id.dialogNameEdit);

        final Button dialogOkButton = nameDialogView.findViewById(R.id.dialogOkButton);
        final Button dialogCancelButton = nameDialogView.findViewById(R.id.dialogCancelButton);

        AlertDialog.Builder nameDialogBuilder = new AlertDialog.Builder(this);

        nameDialogBuilder.setView(nameDialogView);


        final AlertDialog nameDialog = nameDialogBuilder.create();

        nameDialog.show();

        return Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(final SingleEmitter<String> emitter) throws Exception {
                dialogOkButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String username = dialogNameEdit.getText().toString();
                        Log.d("ОТПРАВКА СООБЩЕНИЯ", "Отправка имени");
                        emitter.onSuccess(username);
                        nameDialog.dismiss();
                    }
                });

                dialogCancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nameDialog.dismiss();
                    }
                });
            }
        });
    }
}
