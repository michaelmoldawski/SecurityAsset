package com.formation.appli.securityasset.Model.PhoneLocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.*;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;


/**
 * Created by michael on 09-07-17.
 */

public class GpsLocation implements LocationListener {
    private static Phonelocation phone=null;

    private GpsLocation(Phonelocation phone) {
        this.phone=phone;
    }


    //region callback
    public interface IGpsLocation {

        void getLocation(Phonelocation phonelocation);

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

            instance = new GpsLocation(phone);
        }
        return instance;
    }

    //endregion
    public void askLocation(Context context) {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 50, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (callback != null) {
            callback.getLocation(new Phonelocation(location.getLatitude(),location.getLongitude())
            );
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
