package com.formation.appli.securityasset;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.formation.appli.securityasset.Assynctasks.AssyncWaitTask;

public class MainActivity extends AppCompatActivity implements AssyncWaitTask.IAssyncWaitTask{
    private AssyncWaitTask task;
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
        task=new AssyncWaitTask();
        task.setCallback(this);
        task.execute(intent);



    }

}
