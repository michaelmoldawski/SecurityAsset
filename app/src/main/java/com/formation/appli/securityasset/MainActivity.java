package com.formation.appli.securityasset;

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.formation.appli.securityasset.Assynctasks.AsyncWaitTask;

public class MainActivity extends AppCompatActivity implements AsyncWaitTask.IAsyncWaitTask {
    private AsyncWaitTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StartTask();
    }



    @Override
    public void goToActivity(Intent intent) {
        startActivity(intent);
        finish();
    }

    private void StartTask(){
        Intent intent = new Intent(MainActivity.this, LogActivity.class);
        task=new AsyncWaitTask();
        task.setCallback(this);
        task.execute(intent);

    }

}
