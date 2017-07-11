package com.formation.appli.securityasset.Assynctasks;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.formation.appli.securityasset.ControlActivity;
import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.TimeUnit;

/**
 * Created by michael on 11-07-17.
 */

public class AsyncGetLatLong extends AsyncTask<Void, Void, Location> implements OnSuccessListener<Location> {

    private FusedLocationProviderClient mFusedLocationClient;
    private double latitude;
    private double longitude;
    public Location mylocation;
    public boolean noSuccess = true;

    //region callback
    public interface IAsyncGetLatLong {

        void updateLocation(Location position);

    }

    private IAsyncGetLatLong callback;

    public void setCallback(IAsyncGetLatLong callback) {
        this.callback = callback;
    }

    //endregion

    @Override
    protected Location doInBackground(Void... params) {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(ControlActivity.context);

        if (ActivityCompat.checkSelfPermission(ControlActivity.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(ControlActivity.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //cette partie est rendue obligatoire par la méthode getLastLocation(), rien n'y est mis car
            //cela est géré avant
            return null;
        }

        if (mFusedLocationClient.getLastLocation().addOnSuccessListener(this) != null) {

            while (noSuccess) {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else mylocation = null;

        return mylocation;
    }

    @Override
    protected void onPostExecute(Location location) {
        callback.updateLocation(location);
    }

    @Override
    public void onSuccess(Location location) {
        Log.e("ON_SUCCES", location.toString());
        if (location != null) {

            latitude = location.getLatitude();
            longitude = location.getLongitude();
            mylocation = location;
            noSuccess = false;
        }

    }
}
