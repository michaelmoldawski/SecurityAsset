package com.formation.appli.securityasset.Assynctasks;

import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

/**
 * Created by michael on 05-07-17.
 */
//this class is used to get a timer which trigger, after a delay, the alerte mode
public class TimerAsyncTask extends AsyncTask<Void, Void, Void> {


    //region callback
    public interface ITimerAsyncTask {

        void setAlarm();

    }

    private ITimerAsyncTask callback;

    public void setCallback(ITimerAsyncTask callback) {
        this.callback = callback;
    }

//endregion

    @Override
    protected Void doInBackground(Void... params) {
        try {
            TimeUnit.MILLISECONDS.sleep(10000);
        } catch (InterruptedException e) {
            // Do nothing
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (callback != null) {
            callback.setAlarm();
        }
    }
}


