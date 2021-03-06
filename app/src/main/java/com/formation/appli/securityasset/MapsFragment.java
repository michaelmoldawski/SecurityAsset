package com.formation.appli.securityasset;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.formation.appli.securityasset.Model.PhoneLocation.Phonelocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapsFragment extends Fragment implements OnMapReadyCallback {

    SupportMapFragment mapFragment;
    Phonelocation myPhonelocation;
    FragmentManager fragManager;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragManager = getChildFragmentManager();
        mapFragment= (SupportMapFragment) fragManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    public MapsFragment() {
        // Required empty public constructor
    }

    //region Singleton
    private static MapsFragment instance;

    public static MapsFragment getInstance() {
        if (instance == null) {
            instance = new MapsFragment();
        }
        return instance;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        float zoom = 18;
        LatLng rescue = new LatLng(ControlActivity.helpLocationLatitude, ControlActivity.helpLocationLongitude);
        Marker help=googleMap.addMarker(new MarkerOptions().position(rescue)
                .title("Help me please"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(rescue,zoom));
        help.showInfoWindow();
    }


    //endregion

    //region Communication
    public interface MapsFragmentCallback {
        void mapsPosition(String name);
    }

    private MapsFragmentCallback callback;

    public void setCallback(MapsFragmentCallback callback) {
        this.callback = callback;
    }
    //endregion

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this locationFragment
        View v= inflater.inflate(R.layout.fragment_maps, container, false);
        v = initView(v);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private View initView(View v) {

        return v;
    }


}
