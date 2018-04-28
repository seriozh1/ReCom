package com.s.samsungitschool.recom00.auth;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.s.samsungitschool.recom00.R;

public class RegistrationActivity extends AppCompatActivity {

    EditText loginEt, emailEt, passwordEt;
    Button registerBt;

    private static String login = "";
    private static String password = "";
    private static String email = "";

    // TODO Указать название сервера
    public static String server = "!!!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        loginEt = (EditText) findViewById(R.id.login_registration_et);
        passwordEt = (EditText) findViewById(R.id.password_registration_et);
        emailEt = (EditText) findViewById(R.id.email_registration_et);
        registerBt = (Button) findViewById(R.id.register__registration_bt);


        registerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login = loginEt.getText().toString();
                password = passwordEt.getText().toString();
                email = emailEt.getText().toString();

                try {
                    new SendData().execute();

                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Ошибка при попытки связаться с сервером", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    class SendData extends AsyncTask<Void, Void, Void> {

        String resultString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            //Работа с сервером



            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(getApplicationContext(), "Данные успешно переданы.", Toast.LENGTH_SHORT).show();
        }
    }

}
