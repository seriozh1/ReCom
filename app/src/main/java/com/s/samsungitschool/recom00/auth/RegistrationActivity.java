package com.s.samsungitschool.recom00.auth;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.s.samsungitschool.recom00.R;
import com.s.samsungitschool.recom00.interfaces.RegisterUserService;
import com.s.samsungitschool.recom00.model.BaseUser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";
    private static final String URI_FOR_REGISTRATION = "http/localhost:8080";

    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialogBuilder;

    private EditText loginEt, emailEt, passwordEt, passwordConfirmEt;
    private Button registerBt, entryBt;

    private String login = "";
    private String email = "";
    private String password = "";
    private BaseUser userFromServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        alertDialogBuilder = new AlertDialog.Builder(RegistrationActivity.this);

        loginEt = (EditText) findViewById(R.id.login_registration_et);
        passwordEt = (EditText) findViewById(R.id.password_registration_et);
        emailEt = (EditText) findViewById(R.id.email_registration_et);
        passwordConfirmEt = (EditText) findViewById(R.id.password_confirm_registration_et);
        registerBt = (Button) findViewById(R.id.register__registration_bt);
        entryBt = (Button) findViewById(R.id.enter__registration_bt);


        registerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        entryBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EntryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void submitForm() {

        if (loginEt.getText().toString().equals("")
                || emailEt.getText().toString().equals("")
                || passwordEt.getText().toString().equals("")
                || passwordConfirmEt.getText().toString().equals("")) {

            alertDialogBuilder.setTitle("Что то пошле не так");
            alertDialogBuilder.setMessage("Заполните все поля");
            displayAlert("input_error");
        } else if ( !passwordEt.getText().toString().equals(passwordConfirmEt.getText().toString())) {

            alertDialogBuilder.setTitle("Что то пошле не так");
            alertDialogBuilder.setMessage("Пароли не совпадают");
            displayAlert("input_error");
        } else {
            login = loginEt.getText().toString();
            email = emailEt.getText().toString();
            password = passwordEt.getText().toString();

            new RegisterAsyncTask().execute("");
        }

    }

    class RegisterAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URI_FOR_REGISTRATION)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RegisterUserService registerUserService = retrofit.create(RegisterUserService.class);
            Call<BaseUser> call = registerUserService.register(login, email, password);
            try {
                Response<BaseUser> userResponse = call.execute();
                userFromServer = userResponse.body();
                //Log.d(LOG_TAG, userResponse.body());
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getBaseContext(), "Ошибка" + e, Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }

    private void displayAlert(final String code) {
        alertDialogBuilder.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (code.equals("input_error")) {
                    passwordEt.setText("");
                    passwordConfirmEt.setText("");
                }
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }



    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    /*class SendData extends AsyncTask<Void, Void, Void> {

        String resultString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(getApplicationContext(), "Данные успешно переданы.", Toast.LENGTH_SHORT).show();
        }
    }*/

}
