package com.formation.appli.securityasset.Model.PhonePosition;

/**
 * Created by michael on 05-07-17.
 */
// this class give the position of the phone, not the location
public class Position {

    private float x;
    private float y;
    private float z;
    //region singleton
    private static Position instance;

    public static Position getInstance() {
        if(instance == null) {
            instance = new Position();
        }
        return instance;
    }
    //endregion


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
