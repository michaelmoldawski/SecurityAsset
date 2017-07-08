package com.formation.appli.securityasset.Model;

import com.formation.appli.securityasset.MapsFragment;

/**
 * Created by michael on 08-07-17.
 */

public class Location {

    private double latitude;
    private double longitude;

    public Location() {

    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //region Singleton
    private static Location instance;

    public static Location getInstance() {
        if (instance == null) {
            instance = new Location();
        }
        return instance;
    }
    //endregion

    //region Getter Setter
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    //endregion
}
