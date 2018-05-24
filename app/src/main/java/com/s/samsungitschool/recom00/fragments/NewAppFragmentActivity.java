package com.s.samsungitschool.recom00.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;

import com.google.android.gms.maps.MapFragment;
import com.s.samsungitschool.recom00.R;

public class NewAppFragmentActivity extends Fragment {

    Spinner spinnerParking, spinnerPit, spinnerSign;

    Button btAddress;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_new_app_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //From other Activity

        btAddress = (Button) getView().findViewById(R.id.address_bt);

        spinnerParking = (Spinner) getView().findViewById(R.id.spinnerProblemParking);
        spinnerPit = (Spinner) getView().findViewById(R.id.spinnerProblemPit);
        spinnerSign = (Spinner) getView().findViewById(R.id.spinnerProblemSign);

        //login = getIntent().getStringExtra("et");

        TabHost tabHost = (TabHost) getView().findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Парковка");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Яма");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.tab3);
        tabSpec.setIndicator("Знак");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);



        // TODO Open Map
        /*btAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getView(), MapsActivity.class);
                startActivity(i);
            }
        });*/


        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
