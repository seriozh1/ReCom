package com.s.samsungitschool.recom00.auth;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


import com.s.samsungitschool.recom00.R;
import com.s.samsungitschool.recom00.interfaces.RegisterUserService;
import com.s.samsungitschool.recom00.model.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";
    private static final String URI_FOR_REGISTRATION = "http://188.235.216.130:80";
    //private static final String URI_FOR_REGISTRATION = "http://171.33.253.145:8080";
    //private static final String URI_FOR_REGISTRATION = "localhost:8080";

    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialogBuilderInput, alertDialogBuilderRegister;

    private EditText loginEt, emailEt, passwordEt, passwordConfirmEt;
    private Button registerBt, entryBt;

    private String login = "";
    private String email = "";
    private String password = "";
    private User userFromServer;
    private String serverAnsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        alertDialogBuilderInput = new AlertDialog.Builder(RegistrationActivity.this);
        alertDialogBuilderRegister = new AlertDialog.Builder(RegistrationActivity.this);

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

            alertDialogBuilderInput.setTitle("Ошибка");
            alertDialogBuilderInput.setMessage("Заполните все поля");
            displayAlert("input_error");
        } else if ( !passwordEt.getText().toString().equals(passwordConfirmEt.getText().toString())) {

            alertDialogBuilderInput.setTitle("Ошибка");
            alertDialogBuilderInput.setMessage("Пароли не совпадают");
            displayAlert("input_error");
        } else {
            login = loginEt.getText().toString();
            email = emailEt.getText().toString();
            password = passwordEt.getText().toString();
            password = getHashString(password);

            new RegisterAsyncTask().execute("");
        }

    }

    class RegisterAsyncTask extends AsyncTask<String, String, String> {
        boolean serverError = false;

        @Override
        protected String doInBackground(String... strings) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URI_FOR_REGISTRATION)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RegisterUserService registerUserService = retrofit.create(RegisterUserService.class);
            Call<String> call = registerUserService.registerUser(login, password, email);
            try {
                Response<String> userResponse = call.execute();
                serverAnsString = userResponse.body();

                if (userResponse.code() == 400 || serverAnsString != null) {
                    alertDialogBuilderInput.setTitle("Ошибка");
                    alertDialogBuilderInput.setMessage("Ошибка сервера");
                    displayAlert("input_error");
                } else {
                    if (serverAnsString.equals("Login already registered")) {

                        alertDialogBuilderRegister.setTitle("Ошибка");
                        alertDialogBuilderRegister.setMessage("Пользователь с таким логином уже существует.");
                        displayAlert("registration_login_error");
                    } else if (serverAnsString.equals("Email already registered")) {

                        alertDialogBuilderRegister.setTitle("Ошибка");
                        alertDialogBuilderRegister.setMessage("Пользователь с такой почтой уже существует.");
                        displayAlert("registration_email_error");
                    } else {
                        alertDialogBuilderRegister.setTitle("Успешно");
                        alertDialogBuilderRegister.setMessage("Вы успешно прошли регистрацию.");
                        displayAlert("registration_OK");



                        Intent intent = new Intent(getApplicationContext(), EntryActivity.class);
                        startActivity(intent);
                    }
                }




            } catch (IOException e) {
                e.printStackTrace();
                serverError = true;
                //Toast.makeText(getBaseContext(), "Ошибка сервера" + e, Toast.LENGTH_LONG).show();
            } catch (NullPointerException e) {
                e.printStackTrace();
                serverError = true;
                //Toast.makeText(getBaseContext(), "Ошибка сервера" + e, Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            if (serverError) {
                Toast.makeText(getBaseContext(), "Ошибка сервера", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void displayAlert(final String code) {
        alertDialogBuilderInput.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (code.equals("input_error")) {
                    passwordEt.setText("");
                    passwordConfirmEt.setText("");

                } else if (code.equals("registration_login_error")) {
                    loginEt.setText("");
                    passwordEt.setText("");
                    passwordConfirmEt.setText("");

                } else if (code.equals("registration_email_error")) {
                    emailEt.setText("");
                    passwordEt.setText("");
                    passwordConfirmEt.setText("");

                } else if (code.equals("registration_OK")) {

                }
            }
        });

        if (code.equals("input_error")) {
            AlertDialog alertDialog = alertDialogBuilderInput.create();
            alertDialog.show();
        } else if (code.equals("registration_login_error")
                || code.equals("registration_email_error")
                || code.equals("registration_OK")) {
            AlertDialog alertDialog = alertDialogBuilderRegister.create();
            alertDialog.show();
        }

    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }



    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    String getHashString(String string) {
        String result = string;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Byte.parseByte(string));
            byte[] hash = md.digest();
            result = new String(hash, StandardCharsets.UTF_8);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return result;
    }

}
