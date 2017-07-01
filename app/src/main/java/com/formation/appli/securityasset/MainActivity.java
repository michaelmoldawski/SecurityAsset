package com.formation.appli.securityasset;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WaitTask waitTask= new WaitTask();
        waitTask.execute();
    }



    private class WaitTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.MILLISECONDS.sleep(0);
            } catch (InterruptedException e) {
                // Do nothing
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(MainActivity.this, LogActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
