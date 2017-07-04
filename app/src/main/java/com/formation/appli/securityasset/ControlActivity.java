package com.formation.appli.securityasset;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

public class ControlActivity extends AppCompatActivity  implements SensorEventListener{

    private TextView tv_control_gravity_values;

    private SensorManager SensorManager;
    private Sensor positionSensor;
    private Sensor accelerometer;
    private float position[]= {0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);


    initview();
    }



    private void initview() {
        SensorManager = (SensorManager) this.getSystemService(this.SENSOR_SERVICE);
        tv_control_gravity_values= (TextView) findViewById(R.id.gravityvalue);
        positionSensorSelection();

    }
    @Override
    protected void onResume() {
        super.onResume();
        SensorManager.registerListener(this, positionSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void positionSensorSelection() {
        // on tente de récupérer la liste des sensor de type gravité
        List<Sensor> positionSensorList = SensorManager.getSensorList(Sensor.TYPE_GRAVITY);
        Iterator<Sensor> i =positionSensorList.iterator();
        Sensor gravitytest = null;
        int sensorId;
        while(i.hasNext()){
            gravitytest = i.next();
        }
        if(gravitytest == null){
            positionSensorList = SensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
            i=positionSensorList.iterator();
            while(i.hasNext()){
                gravitytest = i.next();
            }
        }
        sensorId=gravitytest.getType();
        positionSensor = SensorManager.getDefaultSensor(sensorId);
        //Toast.makeText(this,String.valueOf(sensorId),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


            position[0] = event.values[0];
            position[1] = event.values[1];
            position[2] = event.values[2];
            String x=Float.toString(position[0]);
            String y=Float.toString(position[1]);
            String z=Float.toString(position[2]);
            tv_control_gravity_values.setText(x+"\n"+y+"\n"+z);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}