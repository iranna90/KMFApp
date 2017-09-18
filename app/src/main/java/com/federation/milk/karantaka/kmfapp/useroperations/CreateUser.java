package com.federation.milk.karantaka.kmfapp.useroperations;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.federation.milk.karantaka.kmfapp.MyApplication;
import com.federation.milk.karantaka.kmfapp.R;
import com.federation.milk.karantaka.kmfapp.services.HttpUtils;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpResponse;

import static java.lang.String.format;

public class CreateUser extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);
    }

    public void backToMainActivity(View view) {
        setResult(RESULT_OK);
        finish();
    }

    public void onClickStoreUser(View view) throws IOException {
        TextView personId = (TextView) findViewById(R.id.aadhar_id);
        TextView firstName = (TextView) findViewById(R.id.first_name);
        TextView lastName = (TextView) findViewById(R.id.last_name);
        // do rest call under dairy opened
        UserEntity userEntity = new UserEntity(0, firstName.getText().toString(), lastName.getText().toString(), personId.getText().toString());
        createUser(userEntity);
        Intent goingBack = new Intent();
        goingBack.putExtra("user", userEntity);
        setResult(RESULT_OK, goingBack);
        finish();
    }

    private HttpResponse createUser(final UserEntity userEntity) throws IOException {
        String dairyId = ((MyApplication) getApplication()).getDairyId();
        return HttpUtils.post(format("%s/persons/%s", dairyId, userEntity.getPersonId()), userEntity);
    }

    public void onClickCancelAction(View view) {
        backToMainActivity(view);
    }
}
