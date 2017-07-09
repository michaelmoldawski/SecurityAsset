package com.formation.appli.securityasset.Model.PhoneLocation;

import android.location.*;
import android.location.Location;
import android.os.Bundle;


/**
 * Created by michael on 09-07-17.
 */

public class GpsLocation implements LocationListener {

    private GpsLocation() {

    }


    //region callback
    public interface IGpsLocation {

        void setMyLocation();

    }

    private GpsLocation.IGpsLocation callback;

    public void setCallback(GpsLocation.IGpsLocation callback) {
        this.callback = callback;
    }

//endregion

    //region singleton
    //il n'y a qu'unune seule localisation GPS
    private static GpsLocation instance;

    public static GpsLocation getInstance() {
        if (instance == null) {
            instance = new GpsLocation();
        }
        return instance;
    }
    //endregion

    @Override
    public void onLocationChanged(Location location) {
        if (callback != null) {
            callback.setMyLocation();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
