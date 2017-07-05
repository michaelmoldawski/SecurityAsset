package com.formation.appli.securityasset.Assynctasks;

import android.content.Intent;
import android.os.AsyncTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by michael on 05-07-17.
 */

//Cette classe permet d'aller d'une activité à une autre en attendant
//durant un certain temps

public class AssyncWaitTask extends AsyncTask<Intent, Void, Intent> {

    //region callback
    public interface IAssyncWaitTask {

        void goToActivity(Intent intent);

    }

    private IAssyncWaitTask callback;

    public void setCallback(IAssyncWaitTask callback) {
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
