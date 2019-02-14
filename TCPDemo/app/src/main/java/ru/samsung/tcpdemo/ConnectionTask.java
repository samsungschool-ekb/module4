package ru.samsung.tcpdemo;

import android.os.AsyncTask;

public class ConnectionTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        SimpleTcpClient simpleTcpClient = new SimpleTcpClient();
        simpleTcpClient.startClient();
        return null;
    }
}
