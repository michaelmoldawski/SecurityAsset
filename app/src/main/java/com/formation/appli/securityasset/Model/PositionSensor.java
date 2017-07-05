package com.formation.appli.securityasset.Model;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.formation.appli.securityasset.ControlActivity;
import com.formation.appli.securityasset.R;

import java.util.Iterator;
import java.util.List;

/**
 * Created by michael on 05-07-17.
 */

/*cette classe est destinée à choisir le capteur à utiliser ainsi
qu'activer les listener et prendre control de l'interface graphique de controlActivity*/
public class PositionSensor implements SensorEventListener {
    private SensorManager SensorManager;
    private Sensor positionSensor;
    private int sensorId;

    public PositionSensor(Activity activity) {
        SensorManager = (SensorManager) activity.getSystemService(activity.SENSOR_SERVICE);
        positionSensorSelection();
        SensorManager.registerListener(this, positionSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        ControlActivity.phonePosition.setX(event.values[0]);
        ControlActivity.phonePosition.setY(event.values[1]);
        ControlActivity.phonePosition.setZ(event.values[2]);
        String x = Float.toString(ControlActivity.phonePosition.getX());
        String y = Float.toString(ControlActivity.phonePosition.getY());
        String z = Float.toString(ControlActivity.phonePosition.getZ());
        ControlActivity.tv_control_gravity_values.setText(x + "\n" + y + "\n" + z);


        if (ControlActivity.phonePosition.getY() < 3f) {

            //Alerte = true;
            ControlActivity.tvalertestatus.setText("Alerte");
            ControlActivity.tv_control_gravity_values.setBackgroundResource(R.color.Alert_on_color);
        }
        if (ControlActivity.phonePosition.getY() > 3f) {

            //Alerte = false;
            ControlActivity.tvalertestatus.setText("You're fine");
            ControlActivity.tv_control_gravity_values.setBackgroundResource(R.color.Alert_off_color);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void positionSensorSelection() {
        // on tente de récupérer la liste des sensor de type gravité
        List<Sensor> positionSensorList = SensorManager.getSensorList(Sensor.TYPE_GRAVITY);
        Iterator<Sensor> i = positionSensorList.iterator();
        Sensor gravitytest = null;

        while (i.hasNext()) {
            gravitytest = i.next();
        }
        if (gravitytest == null) {
            positionSensorList = SensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
            i = positionSensorList.iterator();
            while (i.hasNext()) {
                gravitytest = i.next();
            }
        }
        sensorId = gravitytest.getType();
        positionSensor = SensorManager.getDefaultSensor(sensorId);
    }
}
