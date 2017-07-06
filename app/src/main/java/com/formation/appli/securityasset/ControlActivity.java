package com.formation.appli.securityasset;


import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.formation.appli.securityasset.Model.Position;
import com.formation.appli.securityasset.Model.PositionSensor;


public class ControlActivity extends AppCompatActivity implements Switch.OnCheckedChangeListener {
    public static TextView tv_control_gravity_values;
    public static Position phonePosition;
    public static boolean Alerte;
    public static TextView tvalertestatus;
    private static PositionSensor positionsensor;
    public static Switch sensorSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        initview();
    }


    private void initview() {
        phonePosition = Position.getInstance();
        tv_control_gravity_values = (TextView) findViewById(R.id.gravityvalue);
        tvalertestatus = (TextView) findViewById(R.id.Alertestatus);
        sensorSwitch = (Switch) findViewById(R.id.SensorSwitch);
        //sensorSwitch.setChecked(true);
        //sensorSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        positionsensor = PositionSensor.getInstance(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SensorManager sensorManager=positionsensor.getSensorManager();
        Sensor positionSensor=positionsensor.getPositionSensor();
        if (isChecked) {
        //sensorManager.registerListener((SensorEventListener) this, positionSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
        sensorManager.unregisterListener((SensorEventListener) this,positionSensor);
        }
    }
}
