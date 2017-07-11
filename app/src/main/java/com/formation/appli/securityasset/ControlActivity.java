package com.formation.appli.securityasset;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.formation.appli.securityasset.Model.PhoneLocation.GpsLocation;
import com.formation.appli.securityasset.Model.PhoneLocation.Phonelocation;
import com.formation.appli.securityasset.Model.PhonePosition.Position;
import com.formation.appli.securityasset.Model.PhonePosition.PositionSensor;
import com.google.android.gms.maps.model.LatLng;


public class ControlActivity extends AppCompatActivity implements GpsLocation.IGpsLocation, DialogInterface.OnClickListener, View.OnClickListener, MapsFragment.MapsFragmentCallback,
        Switch.OnCheckedChangeListener {
    public static TextView tv_control_gravity_values;
    public static TextView tvactuallocation;
    public static Position phonePosition;
    public static boolean Alerte;
    public static TextView tvalertestatus;
    private static PositionSensor positionsensor;
    public static Switch sensorSwitch;
    public static ImageButton alertButton;
    public static MapsFragment locationFragment;
    public static LocationManager locationManager;
    public static LatLng phoneLocation;
    private double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        initview();
        askGpsActivation();
        locate();


    }

    private void initview() {
        phonePosition = Position.getInstance();
        tv_control_gravity_values = (TextView) findViewById(R.id.gravityvalue);
        tvalertestatus = (TextView) findViewById(R.id.Alertestatus);
        tvactuallocation =(TextView) findViewById(R.id.current_location);
        alertButton = (ImageButton) findViewById(R.id.panic_button);
        sensorSwitch = (Switch) findViewById(R.id.SensorSwitch);
        sensorSwitch.setChecked(true);
        sensorSwitch.setOnCheckedChangeListener(this);
        alertButton.setOnClickListener(this);
        locationFragment = MapsFragment.getInstance();
        locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);
        //if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        //}
    }

    private void showMaps() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        locationFragment.setCallback(this);
        transaction.add(R.id.fragment_place, locationFragment);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        positionsensor = PositionSensor.getInstance(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SensorManager sensorManager = positionsensor.getSensorManager();
        Sensor positionSensor = positionsensor.getPositionSensor();
        if (isChecked) {
            sensorManager.registerListener(positionsensor, positionSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            sensorManager.unregisterListener(positionsensor, positionSensor);
            Alerte = false;
        }
    }

    @Override
    public void mapsPosition(String name) {

    }

    @Override
    public void onBackPressed() {

        if (locationFragment.isAdded()) {
            this.getSupportFragmentManager().beginTransaction().remove(locationFragment).commit();
        } else {
            this.finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.panic_button:
                showMaps();
                break;
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                break;

            case DialogInterface.BUTTON_NEGATIVE:
                //No button clicked
                break;
        }

    }

    private void askGpsActivation() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Would you like activate the gps for properer use of the application?").setPositiveButton("Yes", this)
                    .setNegativeButton("No", this).show();
        }
    }

    public void locate() {
        GpsLocation gps = GpsLocation.getInstance();
        gps.setCallback(this);
        gps.askLocation(this);
    }

    @Override
    public void getLocation(Phonelocation phonelocation) {
        longitude=phonelocation.getLatitude();
        latitude=phonelocation.getLongitude();
        tvactuallocation.setText(getString(R.string.user_location,latitude,longitude));
    }
}