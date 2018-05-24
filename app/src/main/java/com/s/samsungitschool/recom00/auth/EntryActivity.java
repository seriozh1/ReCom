package com.s.samsungitschool.recom00.auth;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.s.samsungitschool.recom00.R;
import com.s.samsungitschool.recom00.fragments.ProfileFragmentActivity;
import com.s.samsungitschool.recom00.interfaces.EntryUserService;
import com.s.samsungitschool.recom00.interfaces.RegisterUserService;
import com.s.samsungitschool.recom00.model.User;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EntryActivity extends AppCompatActivity {

    ProfileFragmentActivity profileFragmentActivity;

    private static final String URI_FOR_REGISTRATION = "http://188.235.216.130:80";

    AlertDialog.Builder alertDialogBuilderInput;
    private String serverAnsString = "";

    SharedPreferences sharedPreferences;
    private final String LOGIN = "LOGIN";
    private final String POINTS = "POINTS";

    EditText loginEt, passwordEt;
    Button loginIn, goToRegister;

    private User userFromServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        loginEt = (EditText) findViewById(R.id.login_et);
        passwordEt = (EditText) findViewById(R.id.password_et);
        loginIn = (Button) findViewById(R.id.login_in_bt);
        goToRegister = (Button) findViewById(R.id.register_bt);

        alertDialogBuilderInput = new AlertDialog.Builder(EntryActivity.this);

        loginIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (loginEt.getText().toString().equals("") || passwordEt.getText().toString().equals("")) {
                    alertDialogBuilderInput.setTitle("Ошибка");
                    alertDialogBuilderInput.setMessage("Заполните все поля");
                    displayAlert("input_error");
                } else {
                    new EntryAsyncTask().execute("");
                }

            }
        });

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), RegistrationActivity.class);
                startActivityForResult(i, 1);
            }
        });
    }

    class EntryAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URI_FOR_REGISTRATION)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EntryUserService registerUserService = retrofit.create(EntryUserService.class);
            Call<Object> userResponse = registerUserService.authenticate(loginEt.getText().toString(), passwordEt.getText().toString());

            try {
                Response response = userResponse.execute();
                serverAnsString = response.body().toString();
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (serverAnsString != null) {
                alertDialogBuilderInput.setTitle("Ошибка");
                alertDialogBuilderInput.setMessage("Ошибка сервера");
                displayAlert("input_error");
            } else {
                if (serverAnsString.equals("Authentication failed")) {
                    alertDialogBuilderInput.setTitle("Ошибка авторизации");
                    alertDialogBuilderInput.setMessage("Проверьте вводимые данные и повторите попытку");
                    displayAlert("input_error");
                } else {

                    new getUserAsyncTask().execute("");
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    class getUserAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URI_FOR_REGISTRATION)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            EntryUserService registerUserService = retrofit.create(EntryUserService.class);
            Call<User> call = registerUserService.getUser(loginEt.getText().toString());

            try {
                Response<User> userResponse = call.execute();
                userFromServer = userResponse.body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (userFromServer != null) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(LOGIN, userFromServer.getLogin());
                editor.putInt(POINTS, userFromServer.getPoints());
                editor.apply();

                startProfileFragment();
            }

            return null;
        }
    }

    private void startProfileFragment() {
        /*FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, this);*/
        Intent i = new Intent(getBaseContext(), ProfileFragmentActivity.class);

        String loginString = userFromServer.getLogin();
        int pointInt = userFromServer.getPoints();
        i.putExtra("login", loginString);
        i.putExtra("points", pointInt);

        startActivity(i);
    }

    private void displayAlert(final String code) {
        alertDialogBuilderInput.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (code.equals("input_error")) {
                    passwordEt.setText("");
                }
            }
        });

        AlertDialog alertDialog = alertDialogBuilderInput.create();
        alertDialog.show();

    }

}
