package com.s.samsungitschool.recom00.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.MalformedJsonException;
import com.s.samsungitschool.recom00.MainActivity;
import com.s.samsungitschool.recom00.R;
import com.s.samsungitschool.recom00.auth.RegistrationActivity;
import com.s.samsungitschool.recom00.interfaces.CityService;
import com.s.samsungitschool.recom00.interfaces.EntryUserService;
import com.s.samsungitschool.recom00.interfaces.MapService;
import com.s.samsungitschool.recom00.maps.MapsActivity;
import com.s.samsungitschool.recom00.model.ProblemPoint;
import com.s.samsungitschool.recom00.model.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class NewAppFragmentActivity extends Fragment {

    AlertDialog.Builder ad;
    Context context;

    private static final String URI_FOR_REGISTRATION = "http://37.112.201.156:80";

    AlertDialog.Builder alertDialogBuilderInput;

    Spinner spinnerParking, spinnerPit, spinnerSign;
    private String tabSelected = "tag1";

    Button addressBT, sendBT;
    TextView addressTV;
    EditText commentET;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private final String NEW_POINT_LAT = "NEW_POINT_LAT";
    private final String NEW_POINT_LNG = "NEW_POINT_LNG";
    private final String AUTH_STRING = "AUTH_STRING";
    private final String GET_ADDRESS_FROM_MAP = "GET_ADDRESS_FROM_MAP";

    private double newPointLat;
    private double newPointLng;
    private long pointId = -1;

    private String addPointServerAns = "";
    private String addNoteServerAns = "";
    private String sendComplaintServerAns = "";

    boolean showAlertDialog = false;
    boolean loadSuccessfully = false;
    boolean sendSuccessfully = false;
    boolean getAddressFromMap = false;


    private String authString = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_new_app_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sharedPreferences = getActivity().getSharedPreferences("SP", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean(GET_ADDRESS_FROM_MAP, false);
        editor.apply();

        context = getActivity();

        addressBT = getView().findViewById(R.id.address_bt);
        sendBT = getView().findViewById(R.id.send_bt);

        addressTV = getView().findViewById(R.id.address_tv);
        addressTV.setText("Укажите адрес!");
        commentET = getView().findViewById(R.id.comment_et);

        spinnerParking = getView().findViewById(R.id.spinnerProblemParking);
        spinnerPit = getView().findViewById(R.id.spinnerProblemPit);
        spinnerSign = getView().findViewById(R.id.spinnerProblemSign);
        alertDialogBuilderInput = new AlertDialog.Builder(getActivity());


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

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                tabSelected = tabId;
            }
        });

        addressBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( getActivity(), MapsActivity.class);
                startActivity(i);


                getAddressFromMap = sharedPreferences.getBoolean(GET_ADDRESS_FROM_MAP, false);
                if (getAddressFromMap) {
                    addressTV.setText("Данные адреса успешно сохранены");
                }
            }
        });

        sendBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getAddressFromMap = sharedPreferences.getBoolean(GET_ADDRESS_FROM_MAP, false);
                if (getAddressFromMap) {
                    addressTV.setText("Данные адреса успешно сохранены");
                }

                sharedPreferences = getActivity().getSharedPreferences("SP", MODE_PRIVATE);

                authString = sharedPreferences.getString(AUTH_STRING, "");
                getAddressFromMap = sharedPreferences.getBoolean(GET_ADDRESS_FROM_MAP, false);

                if (!authString.equals("")) {

                    if (!getAddressFromMap) {
                        alertDialogBuilderInput.setTitle("Ошибка");
                        alertDialogBuilderInput.setMessage("Не выбран адресс");
                        showAlertDialog = true;
                        loadSuccessfully = false;
                    } else {
                        sharedPreferences = getActivity().getSharedPreferences("SP", MODE_PRIVATE);
                        newPointLat = (double) sharedPreferences.getFloat(NEW_POINT_LAT, 0);
                        newPointLng = (double) sharedPreferences.getFloat(NEW_POINT_LNG, 0);

                        new loadDataAsyncTask().execute("");
                    }

                } else {
                    alertDialogBuilderInput.setTitle("Ошибка");
                    alertDialogBuilderInput.setMessage("Вы не авторизовались");
                    showAlertDialog = true;
                    loadSuccessfully = false;
                }

                if (loadSuccessfully) {
                    Toast.makeText(getActivity(), "Данные успешно загружены на сервер", Toast.LENGTH_LONG).show();

                    // ============= Send complaint =============
                    new sendComplaintAsyncTask().execute("");
                    // ==========================================

                    if (sendSuccessfully) {
                        Toast.makeText(getActivity(), "Жалоба успешно отправлена", Toast.LENGTH_LONG).show();

                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getActivity(), "Ошибка отправки жалобы", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(getActivity(), "Ошибка загрузки данных на сервер", Toast.LENGTH_LONG).show();
                }
            }


        });

        super.onActivityCreated(savedInstanceState);
    }


    class loadDataAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            // ================= add Point =================

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URI_FOR_REGISTRATION)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            MapService mapService_ToAddPoint = retrofit.create(MapService.class);
            Call<Object> callToAddPoint = mapService_ToAddPoint.addPoint(authString, newPointLat, newPointLng);

            Response response_ToAddPoint = null;
            try {
                response_ToAddPoint = callToAddPoint.execute();
                addPointServerAns = response_ToAddPoint.body().toString();

            } catch (IOException e) {
                loadSuccessfully = false;
                e.printStackTrace();
            }

            if (addPointServerAns != null ) {
                if (!addPointServerAns.equals("")) {

                    if (addPointServerAns.equals("User_not_authorized")) {
                        alertDialogBuilderInput.setTitle("Ошибка");
                        alertDialogBuilderInput.setMessage("Вы не авторизовались");
                        showAlertDialog = true;
                        loadSuccessfully = false;

                    } else if (addPointServerAns.equals("0")) {
                        alertDialogBuilderInput.setTitle("Ошибка");
                        alertDialogBuilderInput.setMessage("Ошибка сервера");
                        showAlertDialog = true;
                        loadSuccessfully = false;

                    } else {
                        pointId = (long) Float.parseFloat(addPointServerAns);

                        loadSuccessfully = true;
                    }
                }
            }


            // ================= add Note =================

            // ======== Message ========

            String messageToSend = commentET.getText().toString();


            String typeOfProblem = "";
            if (tabSelected.equals("tag1")) {
                typeOfProblem = spinnerParking.getSelectedItem().toString();
            } else if (tabSelected.equals("tag2")) {
                typeOfProblem = spinnerPit.getSelectedItem().toString();
            } else if (tabSelected.equals("tag3")) {
                typeOfProblem = spinnerSign.getSelectedItem().toString();
            }

            messageToSend = "Тип проблемы: " + typeOfProblem + " \n " + messageToSend;
            // =========================

            MapService mapService_addNote = retrofit.create(MapService.class);
            Call<Object> call_addNote = mapService_addNote.addNote(authString, pointId, messageToSend);

            Response response_addNote = null;
            try {
                response_addNote = call_addNote.execute();
                
                addNoteServerAns = response_addNote.body().toString();

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addNoteServerAns != null) {
                if (!addNoteServerAns.equals("")) {

                    if (addNoteServerAns.equals("User_not_authorized")) {
                        alertDialogBuilderInput.setTitle("Ошибка");
                        alertDialogBuilderInput.setMessage("Вы не авторизовались");
                        showAlertDialog = true;
                        loadSuccessfully = false;

                    } else if (addNoteServerAns.equals("Point_do_not_exist")) {
                        alertDialogBuilderInput.setTitle("Ошибка");
                        alertDialogBuilderInput.setMessage("Точка не добавлена");
                        showAlertDialog = true;
                        loadSuccessfully = false;

                    } else if (addNoteServerAns.equals("Note_already_exists")) {
                        alertDialogBuilderInput.setTitle("Ошибка");
                        alertDialogBuilderInput.setMessage("Запись уже существует");
                        showAlertDialog = true;
                        loadSuccessfully = false;

                    } else if (addNoteServerAns.equals("Note_saved")) {

                        /*alertDialogBuilderInput.setTitle("Успешно");
                        alertDialogBuilderInput.setMessage("Жалоба упешно загружена на сервер");
                        showAlertDialog = true;
                        loadSuccessfully = true;*/

                        // ============= Send complaint =============
                        new sendComplaintAsyncTask().execute("");
                        // ==========================================



                    } else {

                        alertDialogBuilderInput.setTitle("Ошибка");
                        alertDialogBuilderInput.setMessage("Ошибка загрузки данных");
                        showAlertDialog = true;
                        loadSuccessfully = false;
                    }
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (showAlertDialog) {
                loadSuccessfully = false;
                displayAlert("show_alert_dialog");
            }

        }

    }

        // ================= Send Complaint =================

        class sendComplaintAsyncTask extends AsyncTask<String, String, String> {


            @Override
            protected String doInBackground(String... strings) {

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(URI_FOR_REGISTRATION)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                CityService mapService = retrofit.create(CityService.class);
                Call<Object> call = mapService.sendComplaint(authString, pointId);

                Response response = null;
                try {
                    response = call.execute();
                    sendComplaintServerAns = response.body().toString();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (sendComplaintServerAns != null) {
                    if (!sendComplaintServerAns.equals("")) {

                        if (sendComplaintServerAns.equals("User_not_authorized")) {
                            alertDialogBuilderInput.setTitle("Ошибка");
                            alertDialogBuilderInput.setMessage("Вы не авторизовались");
                            showAlertDialog = true;
                            sendSuccessfully = false;

                        } else if (sendComplaintServerAns.equals("Point_not_found")) {
                            alertDialogBuilderInput.setTitle("Ошибка");
                            alertDialogBuilderInput.setMessage("Точка не найдена");
                            showAlertDialog = true;
                            sendSuccessfully = false;

                        } else if (sendComplaintServerAns.equals("Note_already_exists")) {
                            alertDialogBuilderInput.setTitle("Ошибка");
                            alertDialogBuilderInput.setMessage("Запись уже существует");
                            showAlertDialog = true;
                            sendSuccessfully = false;

                        } else if (sendComplaintServerAns.equals("Message_sent_successfully")) {
                            alertDialogBuilderInput.setTitle("Успешно");
                            alertDialogBuilderInput.setMessage("Жалоба успешно отправлена");
                            showAlertDialog = true;
                            sendSuccessfully = true;

                        } else {

                            alertDialogBuilderInput.setTitle("Ошибка");
                            alertDialogBuilderInput.setMessage("Ошибка отправки");
                            showAlertDialog = true;
                            sendSuccessfully = false;
                        }
                    }
                }

                if (loadSuccessfully) {
                    alertDialogBuilderInput.setTitle("Усешно");
                    alertDialogBuilderInput.setMessage("Данные успешно загружены на сервер");
                    showAlertDialog = true;

                    if (sendSuccessfully) {
                        alertDialogBuilderInput.setTitle("Усешно");
                        alertDialogBuilderInput.setMessage("Жалоба успешно отправлена");
                        showAlertDialog = true;

                        Intent i = new Intent(getActivity(), MainActivity.class);
                        startActivity(i);
                    } else {

                        alertDialogBuilderInput.setTitle("Ошибка");
                        alertDialogBuilderInput.setMessage("Ошибка отправки жалобы");
                        showAlertDialog = true;
                    }

                } else {

                    alertDialogBuilderInput.setTitle("Ошибка");
                    alertDialogBuilderInput.setMessage("Ошибка загрузки данных на сервер");
                    showAlertDialog = true;
                }

                return null;
            }

            // ================================================================

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (showAlertDialog) {
                displayAlert("show_alert_dialog");
            }

        }
    }

    private void displayAlert(final String code) {
        alertDialogBuilderInput.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (code.equals("show_alert_dialog")) {

                }
            }
        });

        AlertDialog alertDialog = alertDialogBuilderInput.create();
        alertDialog.show();

    }
}
