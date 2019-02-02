package ru.samsung.httprequestsbasics.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.samsung.belenkov.R;
import ru.samsung.httprequestsbasics.controller.httpTask.UserTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserTask userTask = new UserTask();
        userTask.execute();
    }
}
