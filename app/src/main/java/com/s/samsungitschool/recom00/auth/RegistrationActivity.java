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

import java.net.ConnectException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    private static final String URI_FOR_REGISTRATION = "http://188.235.192.155:80";
    //private static final String URI_FOR_REGISTRATION = "http://171.33.253.145:8080";
    //private static final String URI_FOR_REGISTRATION = "localhost:8080";

    ProgressDialog progressDialog;
    AlertDialog.Builder alertDialogBuilderInput;

    private EditText loginEt, emailEt, passwordEt, passwordConfirmEt;
    private Button registerBt, entryBt;

    private String login = "";
    private String email = "";
    private String password = "";
    private User userFromServer;
    private String serverAnsString;

    boolean errorInput = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        alertDialogBuilderInput = new AlertDialog.Builder(RegistrationActivity.this);

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
            errorInput = true;
        } else if ( !passwordEt.getText().toString().equals(passwordConfirmEt.getText().toString())) {

            alertDialogBuilderInput.setTitle("Ошибка");
            alertDialogBuilderInput.setMessage("Пароли не совпадают");
            errorInput = true;
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

            Call<Object> registerResponse = registerUserService.registerUser(login, password, email);
            try {
                Response response = registerResponse.execute();
                //serverAnsString = registerResponse.body().toString();
                serverAnsString = response.toString();

                if (serverAnsString == null) {
                    alertDialogBuilderInput.setTitle("Ошибка");
                    alertDialogBuilderInput.setMessage("Ошибка сервера");
                    errorInput = true;
                } else {
                    if (serverAnsString.equals("Login already registered")) {

                        alertDialogBuilderInput.setTitle("Ошибка");
                        alertDialogBuilderInput.setMessage("Пользователь с таким логином уже существует.");
                        errorInput = true;
                    } else if (serverAnsString.equals("Email already registered")) {

                        alertDialogBuilderInput.setTitle("Ошибка");
                        alertDialogBuilderInput.setMessage("Пользователь с такой почтой уже существует.");
                        errorInput = true;
                    } else {
                        alertDialogBuilderInput.setTitle("Успешно");
                        alertDialogBuilderInput.setMessage("Вы успешно прошли регистрацию.");
                        errorInput = true;



                        Intent intent = new Intent(getApplicationContext(), EntryActivity.class);
                        startActivity(intent);
                    }
                }


            } catch (NullPointerException e) {
                e.printStackTrace();
                serverError = true;
                //Toast.makeText(getBaseContext(), "Ошибка сервера" + e, Toast.LENGTH_LONG).show();
            } catch (ConnectException e) {
                serverError = true;
                e.printStackTrace();
            } catch (IOException e) {
                serverError = true;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            if (errorInput) {
                displayAlert("input_error");
            }
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
            AlertDialog alertDialog = alertDialogBuilderInput.create();
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
