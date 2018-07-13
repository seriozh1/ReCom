package com.s.samsungitschool.recom00.fragments;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.s.samsungitschool.recom00.R;
import com.s.samsungitschool.recom00.model.Application;

import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class ApplicationsFragmentActivity extends Fragment {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    RecyclerView rv;
    LinearLayoutManager llm;

    String SPList;
    ArrayList<Application> myApplicationsList = new ArrayList<>();
    private final String MY_APPLICATIONS_LIST = "MY_APPLICATIONS_LIST";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_applications_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("SP", MODE_PRIVATE);

        rv = (RecyclerView) getView().findViewById(R.id.rv);
        llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);


        SPList = sharedPreferences.getString(MY_APPLICATIONS_LIST, "");
        if (SPList.equals("")) {
            Toast.makeText(getActivity(), "Ошибка получения списка жалоб", Toast.LENGTH_SHORT).show();
        } else {
            //myApplicationsList = new Gson().fromJson(SPList);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
