package com.formation.appli.securityasset;


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
import com.formation.appli.securityasset.Model.Position;
import com.formation.appli.securityasset.Model.PositionSensor;


public class ControlActivity extends AppCompatActivity implements View.OnClickListener,MapsFragment.MapsFragmentCallback, Switch.OnCheckedChangeListener {
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
    }

    private void initview() {
        phonePosition = Position.getInstance();
        tv_control_gravity_values = (TextView) findViewById(R.id.gravityvalue);
        tvalertestatus = (TextView) findViewById(R.id.Alertestatus);
        alertButton=(ImageButton) findViewById(R.id.panic_button);
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
            positionSensorStatus=true;
        } else {
            sensorManager.unregisterListener(positionsensor, positionSensor);
            Alerte=false;
            positionSensorStatus=false;
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
}
