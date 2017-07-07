package com.formation.appli.securityasset;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class MapsFragment extends Fragment {

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
