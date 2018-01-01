package com.federation.milk.karantaka.kmfapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.federation.milk.karantaka.kmfapp.token.Session;

public class LoginActivity extends AppCompatActivity {

    private EditText dairyIdView;
    private EditText userNameView;
    private EditText passwordView;
    private Session session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dairyIdView = (EditText) findViewById(R.id.dairyId);
        userNameView = (EditText) findViewById(R.id.userName);
        passwordView = (EditText) findViewById(R.id.password);
        session = new Session(this);
        String token = session.getToken();
        if (token == null) {
            setContentView(R.layout.login_layout);
        }else {
            
        }


    }


    public void login(View view) {
        checkLogin(dairyIdView.getText().toString(), userNameView.getText().toString(), passwordView.getText().toString());
        Intent dairyActivity = new Intent(this, DairyOperationActivity.class);
        startActivity(dairyActivity);
    }

    private void checkLogin(String dairyId, String username, String password) {
        System.out.println("dairy id : " + dairyId + " user: " + username + " password: " + password);
    }
}