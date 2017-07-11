package com.formation.appli.securityasset.Model.PhoneLocation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.*;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;


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

        Log.v("GPS_POS_CHECK","Start Update");

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))   {
            Log.i("GPS_POS_CHECK", "GPS activer");
        }

        if(locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER))   {
            Log.i("GPS_POS_CHECK", "Passive Activer");
        }

        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))   {
            Log.i("GPS_POS_CHECK", "Network Activer");
        }

        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

     //   Criteria criteria = new Criteria();
     //   criteria.setAccuracy(Criteria.ACCURACY_FINE);
     //   criteria.setCostAllowed(false);
     //   Location loc = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria,true));
     //
     //   if(loc != null)  {
     //   Log.i("GPS_POS_CHECK",loc.toString());
     //   }
     //   else {
     //       Log.i("GPS_POS_CHECK","Is null");
     //   }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (callback != null) {
            callback.getLocation(new Phonelocation(location.getLatitude(),location.getLongitude())
            );

            Log.v("GPS_POS_CHECK","Trouvé!");
            //locationManager.removedUpdates(this);
        }
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.i("GPS_POS_CHECK",provider);

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
