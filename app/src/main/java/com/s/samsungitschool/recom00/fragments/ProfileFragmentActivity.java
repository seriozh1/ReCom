package com.s.samsungitschool.recom00.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.s.samsungitschool.recom00.R;
import com.s.samsungitschool.recom00.auth.EntryActivity;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragmentActivity extends Fragment implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    Spinner spinnerProblem, spinnerType;

    Button btExit, btAddress;
    TextView loginTv, pointsTv, rankTv;

    private String login = "-";
    private int points = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btExit = (Button) getView().findViewById(R.id.bt_exit);
        loginTv = (TextView) getView().findViewById(R.id.login_tv);
        pointsTv = (TextView) getView().findViewById(R.id.points_tv);
        rankTv = (TextView) getView().findViewById(R.id.rank_tv);
        btAddress = (Button) getView().findViewById(R.id.address_bt);

        spinnerProblem = (Spinner) getView().findViewById(R.id.spinnerProblem);
        spinnerType = (Spinner) getView().findViewById(R.id.spinnerType);

        //From other Activity
        //TODO SET LOGIN AND POINTS

        //login = getIntent().getStringExtra("et");
        //sharedPreferences = getPreferences(MODE_PRIVATE);

        /*TabHost tabHost = (TabHost) getView().findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tag1");
        tabSpec.setContent(R.id.tab1);
        tabSpec.setIndicator("Кот");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag2");
        tabSpec.setContent(R.id.tab2);
        tabSpec.setIndicator("Кошка");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("tag3");
        tabSpec.setContent(R.id.tab3);
        tabSpec.setIndicator("Котёнок");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);*/

        /*spinnerType.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


            }
        });*/





        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent entryIntent = new Intent(getActivity(), EntryActivity.class);
                startActivity(entryIntent);
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
