package com.formation.appli.securityasset;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.formation.appli.securityasset.Assynctasks.AsyncGeoCoding;
import com.formation.appli.securityasset.Model.PhoneLocation.GpsLocation;
import com.formation.appli.securityasset.Model.PhoneLocation.Phonelocation;
import com.formation.appli.securityasset.Model.PhonePosition.Position;
import com.formation.appli.securityasset.Model.PhonePosition.PositionSensor;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ControlActivity extends AppCompatActivity implements GpsLocation.IGpsLocation,
        DialogInterface.OnClickListener, View.OnClickListener,
        MapsFragment.MapsFragmentCallback, Switch.OnCheckedChangeListener,
        AsyncGeoCoding.IAsyncGeoCoding {

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
    private double latitude, longitude;
    public static Context context;
    public static LatLng mylocation;
    public static TextView tv_contol_activity_geocoding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        context = this;
        initview();
        askGpsActivation();
        locate();
    }

    private void initview() {
        phonePosition = Position.getInstance();
        tv_control_gravity_values = (TextView) findViewById(R.id.gravityvalue);
        tvalertestatus = (TextView) findViewById(R.id.Alertestatus);
        tvactuallocation = (TextView) findViewById(R.id.current_location);
        alertButton = (ImageButton) findViewById(R.id.panic_button);
        sensorSwitch = (Switch) findViewById(R.id.SensorSwitch);
        sensorSwitch.setChecked(true);
        sensorSwitch.setOnCheckedChangeListener(this);
        alertButton.setOnClickListener(this);
        locationFragment = MapsFragment.getInstance();
        locationManager = (LocationManager) getSystemService(this.LOCATION_SERVICE);

        /*TODO enlever l'apiKey du manifeste, afin qu'elle ne dois pas détourner
        TODO et la prendre du JSON pour activer les activiter google*/
        //String Api_Key= getString(R.string.google_api_key);
        tv_contol_activity_geocoding = (TextView) findViewById(R.id.geocoding);
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
//ne sert encore à rien, à implémenter le callback vers le fragment mapsFragment
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
                //aucune action ne dois être effectuée en cas de refus
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
        Log.v("GPS_POS_CHECK", phonelocation.getLongitude() + " - " + phonelocation.getLatitude());
        latitude = phonelocation.getLatitude();
        longitude = phonelocation.getLongitude();
        tvactuallocation.setText(getString(R.string.user_location, latitude, longitude));
        mylocation = new LatLng(latitude, longitude);

        sendApiRequest();
        saveInFirebase();

    }

    private void saveInFirebase() {
        // Write a message to the database
        FirebaseUser currentUser = LogActivity.currentUser;
        String mail = currentUser.getEmail();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase /*= database.getReference("ID")*/;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users/"
                + currentUser.getUid()).child("email");

        mDatabase.setValue(mail);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users/"
                + currentUser.getUid()).child("latitude");
        mDatabase.setValue(latitude);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("users/"
                + currentUser.getUid()).child("longitude");
        mDatabase.setValue(longitude);
    }

    private void sendApiRequest() {
        AsyncGeoCoding task = new AsyncGeoCoding();
        task.setCallback(this);
        double test = latitude;
        String tasktoExecutes = String.valueOf(latitude) + "," + String.valueOf(longitude);
        task.execute(tasktoExecutes);
    }

    @Override
    public void updateGeoCode(String s) {
        String test = s;
        tv_contol_activity_geocoding.setText(s);
    }
}