package ru.samsung.httprequestsbasics.controller.httpTask;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;

import ru.samsung.belenkov.model.api.JsonPlaceholderApi;
import ru.samsung.belenkov.model.entities.User;

public class UserTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        JsonPlaceholderApi api = new JsonPlaceholderApi("https://jsonplaceholder.typicode.com/users");
        try {
            User user = api.getUser(1);
            User user2 = api.getUser(2);

            Log.d(UserTask.class.getName(), String.format("user1:%s\nuser2:%s", user.toString(), user2.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
