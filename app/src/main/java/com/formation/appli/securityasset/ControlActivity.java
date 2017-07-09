package com.formation.appli.securityasset;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.formation.appli.securityasset.Model.PhonePosition.Position;
import com.formation.appli.securityasset.Model.PhonePosition.PositionSensor;


public class ControlActivity extends AppCompatActivity implements DialogInterface.OnClickListener, View.OnClickListener, MapsFragment.MapsFragmentCallback,
        Switch.OnCheckedChangeListener {
    public static TextView tv_control_gravity_values;
    public static Position phonePosition;
    public static boolean positionSensorStatus;
    public static boolean Alerte;
    public static TextView tvalertestatus;
    private static PositionSensor positionsensor;
    public static Switch sensorSwitch;
    public static ImageButton alertButton;
    public static MapsFragment locationFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        initview();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Would you activate the gps for properer use of the application?").setPositiveButton("Yes", this)
                .setNegativeButton("No", this).show();

    }

    private void initview() {
        phonePosition = Position.getInstance();
        tv_control_gravity_values = (TextView) findViewById(R.id.gravityvalue);
        tvalertestatus = (TextView) findViewById(R.id.Alertestatus);
        alertButton = (ImageButton) findViewById(R.id.panic_button);
        sensorSwitch = (Switch) findViewById(R.id.SensorSwitch);
        sensorSwitch.setChecked(true);
        sensorSwitch.setOnCheckedChangeListener(this);
        alertButton.setOnClickListener(this);
    }

    private void showMaps() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        locationFragment = MapsFragment.getInstance();
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
            positionSensorStatus = true;
        } else {
            sensorManager.unregisterListener(positionsensor, positionSensor);
            Alerte = false;
            positionSensorStatus = false;
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
}