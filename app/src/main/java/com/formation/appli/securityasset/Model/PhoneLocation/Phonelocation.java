package com.formation.appli.securityasset.Model.PhoneLocation;

/**
 * Created by michael on 08-07-17.
 */

public class Phonelocation {

    private double latitude;
    private double longitude;

    public Phonelocation() {

    }

    public Phonelocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //region Singleton
    private static Phonelocation instance;

    public static Phonelocation getInstance() {
        if (instance == null) {
            instance = new Phonelocation();
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
