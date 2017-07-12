package com.formation.appli.securityasset.Assynctasks;

import android.os.AsyncTask;

import com.formation.appli.securityasset.Model.Helper.Web.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by michael on 11-07-17.
 */

public class AsyncGeoCoding extends AsyncTask<String, Void, String> {
    //la clé API utilisée n'est pas la même que les googles services, ceux ci n'englobant pas la l'authorisation de l'api
    //à Améliorer! PS: attentation à la limitiation de la clé
    private static final String URL_APPID = "AIzaSyAOQVFMhVVDVsm9rWyt4RJiwfA6ojXEzgs";
    private static final String URL_BASE = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";

    //region Callback
    public interface IAsyncGeoCoding {
        void updateGeoCode(String s);
    }

    private IAsyncGeoCoding callback;

    public void setCallback(IAsyncGeoCoding callback) {
        this.callback = callback;
    }
    //endregion

    @Override
    protected String doInBackground(String... params) {
        if (params == null || params.length != 1) {
            return null;
        }
        String query = params[0];

        String request = URL_BASE + query + "&key=" + URL_APPID;
        HttpHandler sh = new HttpHandler();

        // Making a request to url and getting response
        String jsonStr = sh.makeServiceCall(request);

        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);


                /// Getting JSON Array node
                JSONArray results = jsonObj.getJSONArray("results");
                JSONObject firstResult = results.getJSONObject(0);
                JSONArray addressComponents = firstResult.getJSONArray("address_components");
                JSONObject address = addressComponents.getJSONObject(1);
                String adressestr = address.getString("short_name");

                return adressestr;

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
        return null;
    }


    @Override
    protected void onPostExecute(String geocode) {
        if (callback != null) {
            callback.updateGeoCode(geocode);
        }
    }
}