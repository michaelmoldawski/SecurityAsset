package com.formation.appli.securityasset.Assynctasks;

import android.content.Intent;
import android.os.AsyncTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by michael on 05-07-17.
 */

//Cette classe permet d'aller d'une activité à une autre en attendant
//durant un certain temps

public class AsyncWaitTask extends AsyncTask<Intent, Void, Intent> {

    //region callback
    public interface IAsyncWaitTask {

        void goToActivity(Intent intent);

    }

    private IAsyncWaitTask callback;

    public void setCallback(IAsyncWaitTask callback) {
        this.callback = callback;
    }

//endregion

    @Override
    protected Intent doInBackground(Intent... params) {
        Intent intent = params[0];
        try {
            TimeUnit.MILLISECONDS.sleep(0);
        } catch (InterruptedException e) {
            // Do nothing
        }
        return intent;
    }

    @Override
    protected void onPostExecute(Intent intent) {
        if (callback != null) {
            callback.goToActivity(intent);
        }
    }


}
