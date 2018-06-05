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

        //SET LOGIN AND POINTS

        loadLoginAndPoints();

        loginTv.setText(login);
        pointsTv.setText( Integer.valueOf(points).toString() );

        if (points <= 100) {
            rankTv.setText("Домосед");
        } else if (points <= 300) {
            rankTv.setText("Пешеход");
        } else if (points <= 500) {
            rankTv.setText("Внимательный пешеход");
        } else if (points <= 750) {
            rankTv.setText("Курсант");
        } else if (points <= 1100) {
            rankTv.setText("Инспектор");
        } else if (points <= 1400) {
            rankTv.setText("Старший инспектор");
        } else if (points <= 1700) {
            rankTv.setText("Начальник смены");
        } else if (points <= 2200) {
            rankTv.setText("Командир отделения");
        } else if (points <= 2700) {
            rankTv.setText("Заместитель командира взводад");
        } else if (points <= 3500) {
            rankTv.setText("Командир взвода");
        }

        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent entryIntent = new Intent(getActivity(), EntryActivity.class);
                startActivity(entryIntent);
            }
        });
    }

    void loadLoginAndPoints() {

        sharedPreferences = getActivity().getSharedPreferences("SP", MODE_PRIVATE);

        login = sharedPreferences.getString(LOGIN, "");
        points = sharedPreferences.getInt(POINTS, 0);

        Toast.makeText(getActivity() ,"Date Loaded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
