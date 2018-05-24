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

    Button btExit;
    TextView loginTv, pointsTv, rankTv;


    private final String LOGIN = "LOGIN";
    private final String POINTS = "POINTS";

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

        //TODO SET LOGIN AND POINTS
        loadLoginAndPoints();


        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent entryIntent = new Intent(getActivity(), EntryActivity.class);
                startActivity(entryIntent);
            }
        });
    }

    void loadLoginAndPoints() {
        //sharedPreferences = getPreferences(MODE_PRIVATE);


        //loginTv.setText(sharedPreferences.getString(LOGIN, ""));
        //loginTv.setText(sharedPreferences.getString(POINTS, "0"));

        //Toast.makeText(, "Date Loaded", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
