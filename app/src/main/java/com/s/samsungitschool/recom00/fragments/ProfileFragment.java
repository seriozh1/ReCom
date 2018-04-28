package com.s.samsungitschool.recom00.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.s.samsungitschool.recom00.MainActivity;
import com.s.samsungitschool.recom00.MapsActivity;
import com.s.samsungitschool.recom00.R;
import com.s.samsungitschool.recom00.auth.EntryActivity;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    /*@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btEnter = (Button) getView().findViewById(R.id.bt_enter);
        btEnter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.bt_enter:
                Intent entryIntent = new Intent(getActivity(), EntryActivity.class);
                startActivity(entryIntent);
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }


}
