package com.federation.milk.karantaka.kmfapp.useroperations;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.federation.milk.karantaka.kmfapp.R;

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

    public void onClickStoreUser(View view) {
        TextView personId = (TextView) findViewById(R.id.aadhar_id);
        TextView firstName = (TextView) findViewById(R.id.first_name);
        TextView lastName = (TextView) findViewById(R.id.last_name);
        // do rest call under dairy opened
        UserEntity userEntity = new UserEntity(firstName.getText().toString(), lastName.getText().toString(), personId.getText().toString());
        Toast.makeText(this, userEntity.toString(), Toast.LENGTH_SHORT).show();
        backToMainActivity(view);
    }

    public void onClickCancelAction(View view) {
        backToMainActivity(view);
    }
}
