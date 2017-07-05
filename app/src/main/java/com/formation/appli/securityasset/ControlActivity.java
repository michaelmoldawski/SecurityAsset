package com.formation.appli.securityasset;


import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.formation.appli.securityasset.Model.Position;
import com.formation.appli.securityasset.Model.PositionSensor;


public class ControlActivity extends AppCompatActivity {
    public static TextView tv_control_gravity_values;
    static SensorManager SensorManager;
    private int sensorId;
    public static Position phonePosition;
    static boolean Alerte;
    public static TextView tvalertestatus;
    private static PositionSensor positionsensor;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        positionsensor=new PositionSensor(this);
    }

}
